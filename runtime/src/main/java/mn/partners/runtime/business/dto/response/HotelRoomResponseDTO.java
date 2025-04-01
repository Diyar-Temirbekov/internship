package mn.partners.runtime.business.dto.response;

import lombok.Data;
import mn.partners.runtime.common.enums.RoomType;

@Data
public class HotelRoomResponseDTO {

    private Long id;

    private Long hotelId;

    private RoomType roomType;

    private Double price;

    private Integer totalCountOfRooms;

    private Integer reservedRooms;
}