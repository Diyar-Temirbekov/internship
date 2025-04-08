package mn.partners.runtime.business.service;

import mn.partners.runtime.business.dto.request.HotelRoomRequestDTO;
import mn.partners.runtime.business.dto.response.HotelRoomResponseDTO;
import mn.partners.runtime.common.enums.RoomType;
import mn.partners.runtime.dal.entity.HotelRoomEntity;

import java.util.List;

public interface HotelRoomService {

    HotelRoomResponseDTO createRoom(HotelRoomRequestDTO hotelRoomRequestDTO);

    List<HotelRoomResponseDTO> getAllRoomsToDTO();

    HotelRoomResponseDTO updateHotelRoom(HotelRoomRequestDTO hotelRoomRequestDTO);

    void deleteHotelRoom(Long id);

    List<HotelRoomEntity> getAllHotelRooms();

    HotelRoomEntity getByIdAndRoomType(Long roomId, RoomType roomType);

    void saveRoomEntity(HotelRoomEntity hotelRoomEntity);
}