package MyEat.myeat.controller;

import MyEat.myeat.domain.Rider;
import MyEat.myeat.service.RiderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name="Rider API", description = "라이더 API")
@Controller
@RequiredArgsConstructor
public class RiderController {

    private final RiderService riderService;

    @GetMapping(value = "/riders/new")
    public String createForm(Model model) {
        model.addAttribute("RiderForm", new RiderForm());
        return "riders/createRiderForm";
    }


}
