package mn.partners.runtime.controller.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mn.partners.runtime.business.dto.request.HotelRequestDTO;
import mn.partners.runtime.business.dto.response.HotelResponseDTO;
import mn.partners.runtime.business.service.HotelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hotels")
public class HotelRestController {

    private final HotelService hotelService;

    @GetMapping("/{id}")
    public ResponseEntity<HotelResponseDTO> getHotelById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(hotelService.getHotelDTOById(id));
    }

    @PostMapping
    public ResponseEntity<HotelResponseDTO> createHotel(@RequestBody @Valid HotelRequestDTO hotelRequestDTO) {
        return ResponseEntity.status(201).body(hotelService.createHotel(hotelRequestDTO));
    }

    @PutMapping
    public ResponseEntity<HotelResponseDTO> updateHotel(@RequestBody HotelRequestDTO hotelRequestDTO) {
        return ResponseEntity.status(200).body(hotelService.updateHotel(hotelRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable("id") Long id) {
        hotelService.deleteHotel(id);

        return ResponseEntity.noContent().build();
    }
}