document.addEventListener('DOMContentLoaded', function() {

    document.getElementById('addRoomForm').addEventListener('submit', function(e) {
        e.preventDefault();

        const hotelId = Number(document.getElementById('hotelId').value);
        const roomType = document.getElementById('roomType').value;
        const price = document.getElementById('price').value;
        const totalCountOfRooms = Number(document.getElementById('totalCountOfRooms').value);

        if (!hotelId || !roomType || !price || !totalCountOfRooms) {
            showNotification('Пожалуйста, заполните все поля.');
            return;
        }

        fetch('api/rooms', {
            method: 'POST',
            headers: {'Content-Type': 'application/json' },
            body: JSON.stringify({
                hotelId: hotelId,
                roomType: roomType,
                price: price,
                totalCountOfRooms: totalCountOfRooms,
            })
        })
            .then(response => {
                if (response.ok) {
                    showNotification('Комната успешно добавлена.');
                    window.location.reload();
                } else {
                    showNotification('Ошибка при добавлении комнаты.');
                }
            })
            .catch(error => {
                console.error('Ошибка:', error);
                showNotification('Ошибка при добавлении комнаты.');
            });
    });

    document.querySelectorAll('.editable').forEach(element => {
        element.addEventListener('keydown', function(e) {
            if (e.key === 'Enter') {
                e.preventDefault();

                const roomId = this.getAttribute('data-room-id');
                const field = this.getAttribute('data-field');
                const value = this.innerText.trim();

                if (!value) {
                    showNotification('Поле не может быть пустым.');
                    return;
                }

                const updateData = { id: Number(roomId) };
                if (field === 'hotelId') {
                    updateData.hotelId = Number(value);
                } else if (field === 'roomType') {
                    updateData.roomType = value;
                } else if (field === 'price') {
                    const price = value;
                    if (price >= 0) {
                        updateData.price = price;
                    } else {
                        showNotification('Цена не может быть отрицательной.');
                        return;
                    }
                } else if (field === 'totalCountOfRooms') {
                    const totalCountOfRooms = Number(value);
                    if (totalCountOfRooms >= 0) {
                        updateData.totalCountOfRooms = totalCountOfRooms;
                    } else {
                        showNotification('Количество комнат не может быть отрицательным.');
                        return;
                    }
                }

                fetch(`api/rooms`, {
                    method: 'PUT',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(updateData)
                })
                    .then(response => {
                        if (response.ok) {
                            showNotification('Комната успешно обновлена.');
                            this.blur();
                            this.innerText = value;
                        } else {
                            showNotification('Ошибка при обновлении комнаты.');
                        }
                    })
                    .catch(error => {
                        console.error('Ошибка:', error);
                        showNotification('Ошибка при обновлении комнаты.');
                    });
            }
        });
    });

    window.deleteRoom = function(button) {
        const roomId = button.getAttribute('data-room-id');

        if (confirm('Вы уверены, что хотите удалить эту комнату?')) {
            fetch(`api/rooms/${roomId}`, {
                method: 'DELETE'
            })
                .then(response => {
                    if (response.ok) {
                        button.closest('tr').remove();
                        console.log('Комната успешно удалена');
                    } else {
                        console.error('Ошибка при удалении комнаты');
                    }
                })
                .catch(error => console.error('Ошибка:', error));
        }
    };
});

document.getElementById('notificationModal').addEventListener('hidden.bs.modal', function () {
    console.log('Модальное окно закрыто');
});

const notificationModal = new bootstrap.Modal(document.getElementById('notificationModal'));

function showNotification(message) {
    const notificationModalBody = document.getElementById('notificationModalBody');
    notificationModalBody.textContent = message;
    notificationModal.show();
}