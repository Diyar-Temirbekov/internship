package mn.partners.runtime.controller.view;

import lombok.RequiredArgsConstructor;
import mn.partners.runtime.business.service.HotelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/hotels")
public class HotelController {

    private final HotelService hotelService;

    @GetMapping
    public String showHotelsPage(Model model) {
        model.addAttribute("hotels", hotelService.getAllHotels());

        return "hotel";
    }
}