package mn.partners.runtime.controller.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mn.partners.runtime.business.dto.request.HotelRoomRequestDTO;
import mn.partners.runtime.business.dto.response.HotelRoomResponseDTO;
import mn.partners.runtime.business.service.HotelRoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/rooms")
public class HotelRoomRestController {

    private final HotelRoomService hotelRoomService;

    @PostMapping
    public ResponseEntity<HotelRoomResponseDTO> createRoom(@RequestBody @Valid HotelRoomRequestDTO roomRequestDTO) {
        return ResponseEntity.status(201).body(hotelRoomService.createRoom(roomRequestDTO));
    }

    @PutMapping
    public ResponseEntity<HotelRoomResponseDTO> updateRoom(@RequestBody @Valid HotelRoomRequestDTO roomRequestDTO) {
        return ResponseEntity.status(200).body(hotelRoomService.updateHotelRoom(roomRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable("id") Long id) {
        hotelRoomService.deleteHotelRoom(id);

        return ResponseEntity.noContent().build();
    }
}