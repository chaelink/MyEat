package MyEat.myeat.controller;

import MyEat.myeat.domain.Customer;
import MyEat.myeat.domain.Rider;
import MyEat.myeat.service.ContractService;
import MyEat.myeat.service.DummyDataGenerator;
import MyEat.myeat.service.RiderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class ContractController {

    @Autowired
    private ContractService contractService;

    @Autowired
    private DummyDataGenerator dummyDataGenerator;


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

    @GetMapping("/generateData")
    @ResponseBody
    public String generateData() {
        dummyDataGenerator.generateCustomersAndRidersWithContracts(111, 900);
        return "Data generation completed.";
    }

    @GetMapping("/generateCustomer")
    @ResponseBody
    public String generatecustomerData() {
        dummyDataGenerator.generateCustomers(1070, 87);
        return "Customer Data generation completed.";
    }

    @GetMapping("/generateRes")
    @ResponseBody
    public String generateRes() {
        dummyDataGenerator.generateRestaurant();
        return "Restaurant Data generation completed.";
    }
}
