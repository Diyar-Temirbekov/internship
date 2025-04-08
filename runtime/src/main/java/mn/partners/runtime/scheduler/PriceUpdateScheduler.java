package mn.partners.runtime.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mn.partners.runtime.business.service.HotelRoomService;
import mn.partners.runtime.common.enums.RoomType;
import mn.partners.runtime.dal.entity.HotelRoomEntity;
import mn.partners.runtime.soap.client.BookingSoapClient;
import mn.partners.runtime.soap.dto.client.GetBookedRoomsRequest;
import mn.partners.runtime.soap.dto.client.GetBookedRoomsResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.WebServiceIOException;
import org.springframework.ws.soap.client.SoapFaultClientException;

@Component
@RequiredArgsConstructor
@Log4j2
public class PriceUpdateScheduler {

    private final HotelRoomService hotelRoomService;
    private final BookingSoapClient bookingSoapClient;

    @Value("${price.increasedByPercent}")
    private double increasedByPercent;

    @Value("${price.decreasedByPercent}")
    private double decreasedByPercent;

    @Value("${price.defaultIncrease}")
    private double defaultIncrease;

    @Value("${price.minLimit}")
    private double minLimit;

    @Scheduled(fixedRateString = "${scheduledPriceUpdater.fixedRate}")
    public void updatePriceScheduler() {
        try {
            for (HotelRoomEntity roomEntity : hotelRoomService.getAllHotelRooms()) {
                try {
                    GetBookedRoomsRequest request = new GetBookedRoomsRequest();
                    request.setHotelId(roomEntity.getHotelEntity().getId());
                    request.setRoomType(roomEntity.getRoomType().toString());

                    log.info("Планировщик запускает обновление цен для hotelId = {}, roomType = {}",
                            request.getHotelId(), request.getRoomType());

                    GetBookedRoomsResponse response = bookingSoapClient.getBookedRooms(request);
                    int bookedCount = response.getCountBookedRooms();

                    updateHotelRoomEntity(request.getHotelId(), RoomType.valueOf(request.getRoomType()), bookedCount);
                } catch (SoapFaultClientException e) {
                    log.error("Ошибка SOAP-запроса: {}", e.getMessage());
                }
            }
        } catch (WebServiceIOException e) {
            log.error("Проблемы с соединением... {}", e.getMessage());
        }
    }

    public void updateHotelRoomEntity(Long id, RoomType roomType, int bookedCount) {
        HotelRoomEntity room = hotelRoomService.getByIdAndRoomType(id, roomType);
        int baseBookedCount = room.getReservedRooms();
        int totalRooms = room.getTotalCountOfRooms();
        double currentPrice = room.getPrice();
        double newPrice;
        int threshold = totalRooms / 2;

        if (bookedCount == baseBookedCount) {
            log.info("Цена комнаты осталась без изменений: {}", currentPrice);
            return;
        }

        if (bookedCount > threshold) {
            newPrice = currentPrice * increasedByPercent;
        } else if (bookedCount < threshold) {
            newPrice = currentPrice * decreasedByPercent;
        } else {
            newPrice = currentPrice * defaultIncrease;
        }

        if (newPrice < minLimit) {
            log.warn("Цена ниже минимального предела ({}) и не будет обновлена.", minLimit);
            newPrice = currentPrice;
        }

        if (newPrice != currentPrice) {
            log.info("Цена комнаты обновлена: {} - с {} на {}", room.getRoomType(), currentPrice, newPrice);
            room.setPrice(newPrice);
            room.setReservedRooms(bookedCount);

            hotelRoomService.saveRoomEntity(room);
        } else {
            log.info("Цена комнаты осталась без изменений: {}", currentPrice);
        }
    }
}