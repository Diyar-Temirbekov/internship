package mn.partners.runtime.business.mapper;

import mn.partners.runtime.business.dto.request.HotelRoomRequestDTO;
import mn.partners.runtime.business.dto.response.HotelRoomResponseDTO;
import mn.partners.runtime.dal.entity.HotelRoomEntity;
import org.springframework.stereotype.Component;

@Component
public class HotelRoomMapper {

    public HotelRoomResponseDTO toDTO(HotelRoomEntity room) {
        HotelRoomResponseDTO hotelRoomResponseDTO = new HotelRoomResponseDTO();
        hotelRoomResponseDTO.setId(room.getId());
        hotelRoomResponseDTO.setHotelId(room.getHotelEntity().getId());
        hotelRoomResponseDTO.setRoomType(room.getRoomType());
        hotelRoomResponseDTO.setPrice(room.getPrice());
        hotelRoomResponseDTO.setTotalCountOfRooms(room.getTotalCountOfRooms());
        hotelRoomResponseDTO.setReservedRooms(room.getReservedRooms());

        return hotelRoomResponseDTO;
    }

    public HotelRoomEntity toEntity(HotelRoomRequestDTO hotelRoomRequestDTO) {
        HotelRoomEntity hotelRoomEntity = new HotelRoomEntity();
        hotelRoomEntity.setPrice(hotelRoomRequestDTO.getPrice());
        hotelRoomEntity.setRoomType(hotelRoomRequestDTO.getRoomType());
        hotelRoomEntity.setTotalCountOfRooms(hotelRoomRequestDTO.getTotalCountOfRooms());

        return hotelRoomEntity;
    }
}