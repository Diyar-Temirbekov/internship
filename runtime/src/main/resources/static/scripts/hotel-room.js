document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('addRoomForm')?.addEventListener('submit', function(e) {
        e.preventDefault();

        const hotelName = document.getElementById('hotelName').value.trim();
        const roomType = document.getElementById('roomType').value;
        const price = document.getElementById('price').value;
        const totalCountOfRooms = Number(document.getElementById('totalCountOfRooms').value);

        if (!hotelName || !roomType || !price || !totalCountOfRooms) {
            showNotification('Пожалуйста, заполните все поля.');
            return;
        }

        fetch('/api/rooms', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                hotelName: hotelName,
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

    window.openUpdateModal = function(button) {
        const row = button.closest('tr');
        const cells = row.getElementsByTagName('td');

        const roomId = cells[0].innerText.trim();
        const hotelName = cells[1].innerText.trim();
        const roomType = cells[2].innerText.trim();
        const price = cells[3].innerText.trim();
        const totalCountOfRooms = cells[4].innerText.trim();

        document.getElementById('editRoomId').value = roomId;
        document.getElementById('editHotelName').value = hotelName;
        document.getElementById('editRoomType').value = roomType;
        document.getElementById('editPrice').value = price;
        document.getElementById('editTotalCountOfRooms').value = totalCountOfRooms;

        const editModal = new bootstrap.Modal(document.getElementById('editRoomModal'));
        editModal.show();
    };

    const editRoomForm = document.getElementById('editRoomForm');
    if (editRoomForm) {
        editRoomForm.addEventListener('submit', function(e) {
            e.preventDefault();

            const editRoomId = document.getElementById('editRoomId').value;
            const editHotelName = document.getElementById('editHotelName').value.trim();
            const editRoomType = document.getElementById('editRoomType').value.trim();
            const editPrice = document.getElementById('editPrice').value;
            const editTotalCountOfRooms = Number(document.getElementById('editTotalCountOfRooms').value);

            fetch('/api/rooms', {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    id: editRoomId,
                    hotelName: editHotelName,
                    roomType: editRoomType,
                    price: editPrice,
                    totalCountOfRooms: editTotalCountOfRooms,
                })
            })
                .then(response => {
                    if (response.ok) {
                        showNotification('Комната успешно обновлена.');
                        window.location.reload();
                    } else {
                        showNotification('Ошибка при обновлении комнаты.');
                    }
                })
                .catch(error => {
                    console.error('Ошибка:', error);
                    showNotification('Ошибка при обновлении комнаты.');
                });
        });
    }

    window.deleteRoom = function(button) {
        const roomId = button.getAttribute('data-room-id');

        if (confirm('Вы уверены, что хотите удалить эту комнату?')) {
            fetch(`/api/rooms/${roomId}`, {
                method: 'DELETE',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    id: roomId
                })
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

document.getElementById('notificationModal')?.addEventListener('hidden.bs.modal', function () {
    console.log('Модальное окно закрыто');
});

const notificationModal = new bootstrap.Modal(document.getElementById('notificationModal'));

function showNotification(message) {
    const notificationModalBody = document.getElementById('notificationModalBody');
    notificationModalBody.textContent = message;
    notificationModal.show();
}