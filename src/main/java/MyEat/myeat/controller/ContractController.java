package MyEat.myeat.controller;

import MyEat.myeat.domain.Customer;
import MyEat.myeat.domain.Rider;
import MyEat.myeat.service.ContractService;
import MyEat.myeat.service.RiderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ContractController {

    @Autowired
    private ContractService contractService;


    @Operation(summary = "계약 진행")
    @PostMapping(value = "/riders/contract")
    public String contractRider(@RequestParam("riderId") Long riderId, HttpSession session) {
        if(session.getAttribute("customerLoggedIn") == null) {
            return "customers/login";
        }
        Customer customer = (Customer) session.getAttribute("customerLoggedIn");
        Long customerId = customer.getId();


        contractService.contract(riderId, customerId);

        return "customers/customerHome";
    }
}
