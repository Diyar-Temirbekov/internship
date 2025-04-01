package mn.partners.runtime.scheduler;

import lombok.RequiredArgsConstructor;
import mn.partners.runtime.business.service.HotelRoomService;
import mn.partners.runtime.dal.entity.HotelRoomEntity;
import mn.partners.runtime.soap.client.BookingSoapClient;
import mn.partners.runtime.soap.dto.client.GetBookedRoomsRequest;
import mn.partners.runtime.soap.dto.client.GetBookedRoomsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.WebServiceIOException;
import org.springframework.ws.soap.client.SoapFaultClientException;

@Component
@RequiredArgsConstructor
public class PriceUpdateScheduler {

    private static final Logger logger = LoggerFactory.getLogger(PriceUpdateScheduler.class);

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

                    logger.info("Планировщик запускает обновление цен для hotelId = {}, roomType = {}",
                            request.getHotelId(), request.getRoomType());

                    GetBookedRoomsResponse response = bookingSoapClient.getBookedRooms(request);
                    int bookedCount = response.getCountBookedRooms();

                    updateHotelRoomEntity(request.getHotelId(), request.getRoomType(), bookedCount);
                } catch (SoapFaultClientException e) {
                    logger.error("Ошибка SOAP-запроса: {}", e.getMessage());
                }
            }
        } catch (WebServiceIOException e) {
            logger.error("Проблемы с соединением... {}", e.getMessage());
        }
    }

    public void updateHotelRoomEntity(Long id, String roomType, int bookedCount) {
        HotelRoomEntity room = hotelRoomService.getByIdAndRoomType(id, roomType);
        int baseBookedCount = room.getReservedRooms();
        int totalRooms = room.getTotalCountOfRooms();
        double currentPrice = room.getPrice();
        double newPrice;
        int threshold = totalRooms / 2;

        if (bookedCount == baseBookedCount) {
            logger.info("Цена комнаты осталась без изменений: {}", currentPrice);
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
            logger.warn("Цена ниже минимального предела ({}) и не будет обновлена.", minLimit);
            newPrice = currentPrice;
        }

        if (newPrice != currentPrice) {
            logger.info("Цена комнаты обновлена: {} - с {} на {}", room.getRoomType(), currentPrice, newPrice);
            room.setPrice(newPrice);
            room.setReservedRooms(bookedCount);

            hotelRoomService.saveRoomEntity(room);
        } else {
            logger.info("Цена комнаты осталась без изменений: {}", currentPrice);
        }
    }
}