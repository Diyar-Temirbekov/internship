package mn.partners.runtime.business.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import mn.partners.runtime.common.enums.RoomType;

@Data
public class HotelRoomRequestDTO {

    private Long id;
    
    @NotBlank
    private String hotelName;

    @NotNull
    private RoomType roomType;

    @NotNull
    @PositiveOrZero
    private Double price;

    @NotNull
    @PositiveOrZero
    private Integer totalCountOfRooms;
}