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

    window.openEditHotelModal = function(button) {
        const row = button.closest('tr');
        const cells = row.getElementsByTagName('td');

        const hotelId = cells[0].innerText.trim();
        const hotelName = cells[1].innerText.trim();
        const hotelAddress = cells[2].innerText.trim();
        const rooms = cells[3].innerText.trim();


        // Заполняем данные в форму
        document.getElementById('editHotelId').value = hotelId;
        document.getElementById('editHotelName').value = hotelName;
        document.getElementById('editHotelAddress').value = hotelAddress;
        document.getElementById('editRooms').value = rooms;

        // Показываем модальное окно
        const editModal = new bootstrap.Modal(document.getElementById('editHotelModal'));
        editModal.show();
    };

    const editHotelForm = document.getElementById('editHotelForm');
    if (editHotelForm) {
        editHotelForm.addEventListener('submit', function (e) {
            e.preventDefault();

            const editHotelId = document.getElementById('editHotelId').value;
            const editHotelName = document.getElementById('editHotelName').value.trim();
            const editHotelAddress = document.getElementById('editHotelAddress').value.trim();
            const editRooms = document.getElementById('editRooms').value.trim();

            fetch('/api/hotels', {
                method: 'PUT',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({
                    hotelId: editHotelId,
                    hotelName: editHotelName,
                    hotelAddress: editHotelAddress,
                    rooms: editRooms
                })
            })
                .then(response => {
                    if (response.ok) {
                        showNotification('Отель успешно обновлен.');
                        window.location.reload();
                    } else {
                        showNotification('Ошибка при обновлении отеля.');
                    }
                })
                .catch(error => {
                    console.error('Ошибка:', error);
                    showNotification('Ошибка при обновлении отеля.');
                });
        });
    }

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