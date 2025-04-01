package mn.partners.runtime.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class RedirectController {

    @GetMapping("/toHotels")
    public RedirectView redirectToHotels() {
        return new RedirectView("/hotels");
    }

    @GetMapping("/toRooms")
    public RedirectView redirectToRooms() {
        return new RedirectView("/rooms");
    }
}