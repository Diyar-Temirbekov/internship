package mn.partners.runtime.business.service.implementation;

import lombok.RequiredArgsConstructor;
import mn.partners.runtime.business.dto.request.HotelRoomRequestDTO;
import mn.partners.runtime.business.dto.response.HotelRoomResponseDTO;
import mn.partners.runtime.business.mapper.HotelRoomMapper;
import mn.partners.runtime.business.service.HotelRoomService;
import mn.partners.runtime.business.service.HotelService;
import mn.partners.runtime.common.enums.RoomType;
import mn.partners.runtime.common.exception.BusinessException;
import mn.partners.runtime.dal.entity.HotelEntity;
import mn.partners.runtime.dal.entity.HotelRoomEntity;
import mn.partners.runtime.dal.repository.HotelRoomRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelRoomServiceImplementation implements HotelRoomService {

    private final HotelRoomRepository hotelRoomRepository;
    private final HotelRoomMapper hotelRoomMapper;
    private final HotelService hotelService;

    public HotelRoomResponseDTO createRoom(HotelRoomRequestDTO hotelRoomRequestDTO) {
        HotelEntity hotel = hotelService.getHotelById(hotelRoomRequestDTO.getHotelId());
        HotelRoomEntity hotelRoomEntity = hotelRoomMapper.toEntity(hotelRoomRequestDTO);
        hotelRoomEntity.setHotelEntity(hotel);
        hotelRoomEntity.setReservedRooms(0);

        return hotelRoomMapper.toDTO(hotelRoomRepository.save(hotelRoomEntity));
    }

    public HotelRoomResponseDTO updateHotelRoom(HotelRoomRequestDTO hotelRoomRequestDTO) {
        HotelRoomEntity existingHotelRoom = getHotelRoomById(hotelRoomRequestDTO.getId());
        if (hotelRoomRequestDTO.getHotelId() != null) {
            existingHotelRoom.setHotelEntity(hotelService.getHotelById(hotelRoomRequestDTO.getHotelId()));
        }
        if (hotelRoomRequestDTO.getRoomType() != null) {
            existingHotelRoom.setRoomType(RoomType.fromString(hotelRoomRequestDTO.getRoomType().name()));
        }
        if (hotelRoomRequestDTO.getPrice() != null && hotelRoomRequestDTO.getPrice() >= 0) {
            existingHotelRoom.setPrice(hotelRoomRequestDTO.getPrice());
        }
        if (hotelRoomRequestDTO.getTotalCountOfRooms() != null && hotelRoomRequestDTO.getTotalCountOfRooms() >= 0) {
            int newTotal = hotelRoomRequestDTO.getTotalCountOfRooms();

            if (existingHotelRoom.getReservedRooms() >= newTotal) {
                throw new BusinessException("Доступное количество номеров не может превышать общее количество");
            }
            existingHotelRoom.setTotalCountOfRooms(newTotal);
        }

        return hotelRoomMapper.toDTO(hotelRoomRepository.save(existingHotelRoom));
    }

    public void deleteHotelRoom(Long id) {
        if (!hotelRoomRepository.existsById(id)) {
            throw new BusinessException("Номер с ID " + id + " не найден");
        }
        hotelRoomRepository.deleteById(id);
    }

    public HotelRoomEntity getByIdAndRoomType(Long roomId, String roomType) {
        if (roomId == null || roomType == null) {
            throw new BusinessException("RoomId или RoomType не должны быть null");
        }

        return hotelRoomRepository.findByHotelEntityIdAndRoomType(roomId, RoomType.fromString(roomType));
    }

    public void saveRoomEntity(HotelRoomEntity hotelRoomEntity) {
        hotelRoomRepository.save(hotelRoomEntity);
    }

    public List<HotelRoomEntity> getAllHotelRooms() {
        return hotelRoomRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public List<HotelRoomResponseDTO> getAllRoomsToDTO() {
        return getAllHotelRooms().stream()
                .map(hotelRoomMapper::toDTO).collect(Collectors.toList());
    }

    public HotelRoomEntity getHotelRoomById(Long id) {
        if (id == null) {
            throw new BusinessException("RoomId не должен быть null");
        }

        return hotelRoomRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Номер не найден"));
    }
}