package mn.partners.runtime.business.mapper;

import lombok.RequiredArgsConstructor;
import mn.partners.runtime.business.dto.request.HotelRequestDTO;
import mn.partners.runtime.business.dto.response.HotelResponseDTO;
import mn.partners.runtime.dal.entity.HotelEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class HotelMapper {

    private final HotelRoomMapper hotelRoomMapper;

    public HotelResponseDTO toDTO(HotelEntity hotelEntity) {
        HotelResponseDTO hotelResponseDTO = new HotelResponseDTO();
        hotelResponseDTO.setId(hotelEntity.getId());
        hotelResponseDTO.setName(hotelEntity.getName());
        hotelResponseDTO.setAddress(hotelEntity.getAddress());
        hotelResponseDTO.setRooms(hotelEntity.getRooms().stream().map(hotelRoomMapper::toDTO).collect(Collectors.toList()));

        return hotelResponseDTO;
    }

    public HotelEntity toEntity(HotelRequestDTO hotelRequestDTO) {
        HotelEntity hotelEntity = new HotelEntity();
        hotelEntity.setName(hotelRequestDTO.getHotelName());
        hotelEntity.setAddress(hotelRequestDTO.getHotelAddress());

        return hotelEntity;
    }
}