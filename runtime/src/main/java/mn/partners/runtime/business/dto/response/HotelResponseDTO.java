package mn.partners.runtime.business.dto.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class HotelResponseDTO {

    private Long id;

    private String name;

    private String address;

    private List<HotelRoomResponseDTO> rooms = new ArrayList<>();
}