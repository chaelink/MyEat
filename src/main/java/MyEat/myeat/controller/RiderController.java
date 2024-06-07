package MyEat.myeat.controller;

import MyEat.myeat.domain.ContractStatus;
import MyEat.myeat.domain.Rider;
import MyEat.myeat.service.RiderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

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

    @Operation(summary = "Rider 등록")
    @PostMapping(value = "/riders/new")
    public String createRider(@Valid RiderForm riderForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "riders/createRiderForm";
        }
        Rider rider = new Rider();
        rider.setLoginId(riderForm.getLoginId());
        rider.setPassword(riderForm.getPassword());
        rider.setName(riderForm.getName());
        rider.setPhoneNumber(riderForm.getPhoneNumber());
        rider.setIntroduction(riderForm.getIntroduction());
        rider.setStatus(ContractStatus.OFF);
        riderService.join(rider);
        return "redirect:/";
    }

    @Operation(summary = "계약 가능한 Rider 목록")
    @GetMapping(value = "/riders/list")
    public String showContractYetRiders(Model model) {
        List<Rider> riders = riderService.findContractYet();
        model.addAttribute("riders", riders);
        return "riders/contractYetRidersList";

    }


}
