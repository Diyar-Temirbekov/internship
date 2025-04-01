package mn.partners.runtime.business.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class HotelRequestDTO {

    private Long hotelId;

    @NotBlank
    private String hotelName;

    @NotBlank
    private String hotelAddress;
}