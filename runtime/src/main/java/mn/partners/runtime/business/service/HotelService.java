package mn.partners.runtime.business.service;

import mn.partners.runtime.business.dto.request.HotelRequestDTO;
import mn.partners.runtime.business.dto.response.HotelResponseDTO;
import mn.partners.runtime.dal.entity.HotelEntity;

import java.util.List;

public interface HotelService {

    HotelResponseDTO createHotel(HotelRequestDTO hotelRequestDTO);

    List<HotelResponseDTO> getAllHotels();

    HotelResponseDTO updateHotel(HotelRequestDTO hotelRequestDTO);

    void deleteHotel(Long id);

    HotelEntity getHotelByName(String name);

    HotelEntity getHotelById(Long id);

    HotelResponseDTO getHotelDTOById(Long id);
}