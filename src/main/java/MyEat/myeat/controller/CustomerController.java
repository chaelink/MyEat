package MyEat.myeat.controller;

import MyEat.myeat.domain.Customer;
import MyEat.myeat.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping(value = "/members/new")
    public String createForm(Model model) {
        model.addAttribute("CustomerForm", new CustomerForm());
        return "members/createCustomerForm";
    }

    @PostMapping(value = "/members/new")
    public String create(@Valid CustomerForm customerForm, BindingResult result) {
        if (result.hasErrors()) {
            return "members/createCustomerForm";
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
}
