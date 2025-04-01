package mn.partners.runtime.controller.view;

import lombok.RequiredArgsConstructor;
import mn.partners.runtime.business.service.HotelRoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class HotelRoomController {

    private final HotelRoomService hotelRoomService;

    @GetMapping
    public String showRoomsPage(Model model) {
        model.addAttribute("rooms", hotelRoomService.getAllRoomsToDTO());

        return "room";
    }
}