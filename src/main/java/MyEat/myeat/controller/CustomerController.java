package MyEat.myeat.controller;

import MyEat.myeat.domain.Customer;
import MyEat.myeat.service.CustomerService;
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

@Tag(name="Customer API", description = "개인 고객 API")
@Controller
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    //@Hidden
    @GetMapping(value = "/customers/new")
    public String createForm(Model model) {
        model.addAttribute("CustomerForm", new CustomerForm());
        return "customers/createCustomerForm";
    }


    @Operation(summary = "Customer 등록")
    @PostMapping(value = "/customers/new")
    public String create(@Valid CustomerForm customerForm, BindingResult result) {
        if (result.hasErrors()) {
            return "customers/createCustomerForm";
        }

        Customer customer = new Customer();
        customer.setLoginId(customerForm.getLoginId());
        customer.setPassword(customerForm.getPassword());
        customer.setName(customerForm.getName());
        customer.setAddress(customerForm.getAddress());
        customer.setPhoneNumber(customerForm.getPhoneNumber());

        customerService.join(customer);
        return "redirect:/";
    }



    @Operation(summary = "Customer 목록 조회")
    @GetMapping(value= "/customers")
    public String list(Model model) {
        List<Customer> customers = customerService.findAll();
        model.addAttribute("customers", customers);
        return "customers/customerList";
    }
}
