package mn.partners.runtime.business.service.implementation;

import lombok.RequiredArgsConstructor;
import mn.partners.runtime.business.dto.request.HotelRequestDTO;
import mn.partners.runtime.business.dto.response.HotelResponseDTO;
import mn.partners.runtime.business.mapper.HotelMapper;
import mn.partners.runtime.business.service.HotelService;
import mn.partners.runtime.common.exception.BusinessException;
import mn.partners.runtime.dal.entity.HotelEntity;
import mn.partners.runtime.dal.repository.HotelRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelServiceImplementation implements HotelService {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    public HotelResponseDTO createHotel(HotelRequestDTO hotelRequestDTO) {
        HotelEntity hotelEntity = hotelMapper.toEntity(hotelRequestDTO);

        return hotelMapper.toDTO(hotelRepository.save(hotelEntity));
    }

    public List<HotelResponseDTO> getAllHotels() {
        return hotelRepository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream()
                .map(hotelMapper::toDTO).collect(Collectors.toList());
    }

    public HotelResponseDTO updateHotel(HotelRequestDTO hotelRequestDTO) {
        HotelEntity existingHotelEntity = getHotelById(hotelRequestDTO.getHotelId());
        if (hotelRequestDTO.getHotelName() != null) {
            existingHotelEntity.setName(hotelRequestDTO.getHotelName());
        }

        if (hotelRequestDTO.getHotelAddress() != null) {
            existingHotelEntity.setAddress(hotelRequestDTO.getHotelAddress());
        }

        return hotelMapper.toDTO(hotelRepository.save(existingHotelEntity));
    }

    public void deleteHotel(Long id) {
        if (!hotelRepository.existsById(id)) {
            throw new BusinessException("Номер с ID " + id + " не найден");
        }
        hotelRepository.deleteById(id);
    }

    public HotelEntity getHotelByName(String name) {
        if (name == null) {
            throw new BusinessException("Имя отеля не должен быть null");
        }

        return hotelRepository.findByName(name)
                .orElseThrow(() -> new BusinessException("Отель не найден"));
    }

    public HotelEntity getHotelById(Long id) {
        if (id == null) {
            throw new BusinessException("ID отеля не должен быть null");
        }

        return hotelRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Отель не найден"));
    }

    public HotelResponseDTO getHotelDTOById(Long id) {
        return hotelMapper.toDTO(getHotelById(id));
    }
}