package mn.partners.runtime.soap.endpoint;

import lombok.RequiredArgsConstructor;
import mn.partners.runtime.business.service.HotelRoomService;
import mn.partners.runtime.soap.dto.service.GetAllRoomPricesRequest;
import mn.partners.runtime.soap.dto.service.GetAllRoomPricesResponse;
import mn.partners.runtime.soap.dto.service.HotelRoomPrice;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;
import java.util.stream.Collectors;


@Endpoint
@RequiredArgsConstructor
public class HotelsRoomPriceEndpoint {

    private static final String NAMESPACE_URI = "http://partners.mn/runtime/soap";
    private final HotelRoomService hotelRoomService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllRoomPricesRequest")
    @ResponsePayload
    public GetAllRoomPricesResponse getHotelRoomsPrices(@RequestPayload GetAllRoomPricesRequest request) {
        List<HotelRoomPrice> hotelRoomPrice = hotelRoomService.getAllHotelRooms().stream()
                .map(room -> {
                    HotelRoomPrice dto = new HotelRoomPrice();
                    dto.setHotelId(room.getHotelEntity().getId());
                    dto.setHotelName(room.getHotelEntity().getName());
                    dto.setRoomId(room.getId());
                    dto.setRoomType(room.getRoomType().toString());
                    dto.setTotalRoomsAmount(room.getTotalCountOfRooms());
                    dto.setPrice(room.getPrice());

                    return dto;
                }).collect(Collectors.toList());

        GetAllRoomPricesResponse response = new GetAllRoomPricesResponse();
        response.getHotels().addAll(hotelRoomPrice);

        return response;
    }
}