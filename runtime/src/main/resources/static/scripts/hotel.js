document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('addHotelForm').addEventListener('submit', function (e) {
        e.preventDefault();

        const hotelName = document.getElementById('name').value.trim();
        const hotelAddress = document.getElementById('address').value.trim();

        if (!hotelName || !hotelAddress) {
            showNotification('Пожалуйста, заполните все поля.');
            return;
        }

        fetch('api/hotels', {
            method: 'POST',
            headers: {'Content-Type': 'application/json' },
            body: JSON.stringify({hotelName, hotelAddress})
        })
            .then(response => {
                if (response.ok) {
                    showNotification('Отель успешно добавлен.');
                    window.location.reload();
                } else {
                    showNotification('Ошибка при добавлении отеля.');
                }
            })
            .catch(error => {
                console.error('Ошибка:', error);
                showNotification('Ошибка при добавлении отеля.');
            });
    });

    document.querySelectorAll('.editable').forEach(element => {
        element.addEventListener('keydown', function (e) {
            if (e.key === 'Enter') {
                e.preventDefault();

                const hotelId = this.getAttribute('data-hotel-id');
                const field = this.getAttribute('data-field');
                const value = this.innerText.trim();

                if (!value) {
                    showNotification('Поле не может быть пустым.');
                    return;
                }

                const updateData = {hotelId: Number(hotelId)};
                if (field === 'name') {
                    updateData.hotelName = value;
                } else if (field === 'address') {
                    updateData.hotelAddress = value;
                }


                fetch(`api/hotels`, {
                    method: 'PUT',
                    headers: {'Content-Type': 'application/json' },
                    body: JSON.stringify(updateData)
                })
                    .then(response => {
                        if (response.ok) {
                            showNotification('Отель успешно обновлен.');
                            this.blur();
                            this.innerText = value;
                        } else {
                            showNotification('Ошибка при обновлении отеля.');
                        }
                    })
                    .catch(error => {
                        console.error('Ошибка:', error);
                        showNotification('Ошибка при обновлении отеля.');
                    });
            }
        });
    });

    window.deleteHotel = function (button) {
        const hotelId = button.getAttribute('data-hotel-id');

        if (confirm('Вы уверены, что хотите удалить этот отель?')) {
            fetch(`api/hotels/${hotelId}`, {
                method: 'DELETE'
            })
                .then(response => {
                    if (response.ok) {
                        button.closest('tr').remove();
                        console.log("Удалено")
                    } else {
                        console.log("Ошибка")
                    }
                })
                .catch(error => {
                    console.error('Ошибка:', error);
                    showNotification('Ошибка при удалении отеля.');
                });
        }
    };

    document.querySelectorAll('.view-rooms-btn').forEach(button => {
        button.addEventListener('click', function() {
            const hotelId = this.getAttribute('data-hotel-id');

            fetch(`api/hotels/${hotelId}`)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Ошибка при загрузке комнат');
                    }
                    return response.json();
                })
                .then(hotel => {
                    const rooms = hotel.rooms;
                    const roomsTableBody = document.getElementById('roomsTableBody');
                    roomsTableBody.innerHTML = '';

                    rooms.forEach(room => {
                        const row = document.createElement('tr');
                        row.innerHTML = `
                        <td>${room.id}</td>
                        <td>${room.roomType}</td>
                        <td>${room.price}</td>
                        <td>${room.totalCountOfRooms}</td>
                        <td>${room.reservedRooms}</td>
                    `;
                        roomsTableBody.appendChild(row);
                    });

                    new bootstrap.Modal(document.getElementById('roomsModal')).show();
                })
                .catch(error => console.error('Ошибка при загрузке данных отеля:', error));
        });
    });
});
const roomsModal = document.getElementById('roomsModal');
roomsModal.addEventListener('hidden.bs.modal', function () {
    document.body.classList.remove('modal-open');
    document.querySelector('.modal-backdrop')?.remove();
});

function showNotification(message) {
    const notificationModalBody = document.getElementById('notificationModalBody');
    notificationModalBody.textContent = message;
    const notificationModal = new bootstrap.Modal(document.getElementById('notificationModal'), { backdrop: false });
    notificationModal.show();
}