package MyEat.myeat.controller;

import MyEat.myeat.domain.ContractStatus;
import MyEat.myeat.domain.Customer;
import MyEat.myeat.domain.Rider;
import MyEat.myeat.service.CustomerService;
import MyEat.myeat.service.RiderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

@Tag(name="Rider API", description = "라이더 API")
@Controller
@RequiredArgsConstructor
public class RiderController {

    private final RiderService riderService;
    private final CustomerService customerService;

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
        Rider rider = riderService.createRiderFromForm(riderForm);
        riderService.join(rider);
        return "redirect:/";
    }

    @Operation(summary = "계약 가능한 Rider 목록")
    @GetMapping(value = "/riders/list")
    public String showContractYetRiders(HttpSession session, Model model, Pageable pageable) {
        if(session.getAttribute("customerLoggedIn") == null) {
            return "customers/login";
        }

        Page<Rider> riders = riderService.findContractYet(pageable);
        model.addAttribute("riders", riders);
        return "riders/contractYetRidersList";
    }

    @Operation(summary = "계약 진행")
    @PostMapping(value = "/riders/contract")
    public String contractRider(@RequestParam("riderId") Long riderId, HttpSession session, Model model) {
        if(session.getAttribute("customerLoggedIn") == null) {
            return "customers/login";
        }

        riderService.contractRider(riderId);
        Customer customer = (Customer) session.getAttribute("customerLoggedIn");
        Long customerId = customer.getId();
        customerService.contractCustomer(customerId);
        return "customers/customerHome";
    }


}
