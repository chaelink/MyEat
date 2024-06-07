package MyEat.myeat.controller;

import MyEat.myeat.domain.ContractStatus;
import MyEat.myeat.domain.Customer;
import MyEat.myeat.service.CustomerService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name="Customer API", description = "개인 고객 API")
@Controller
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @Hidden
    @GetMapping(value = "/customers/new")
    public String createForm(Model model) {
        model.addAttribute("CustomerForm", new CustomerForm());
        return "customers/createCustomerForm";
    }


    @Operation(summary = "Customer 등록")
    @PostMapping(value = "/customers/new")
    public String create(@Valid CustomerForm customerForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> System.out.println(error.toString()));
            return "customers/createCustomerForm";
        }

        Customer customer = new Customer();
        customer.setLoginId(customerForm.getLoginId());
        customer.setPassword(customerForm.getPassword());
        customer.setName(customerForm.getName());
        customer.setAddress(customerForm.getAddress());
        customer.setPhoneNumber(customerForm.getPhoneNumber());
        customer.setStatus(ContractStatus.OFF);

        try {
            customerService.join(customer);
        } catch (CustomerService.DuplicateCustomerException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("showAlert",true);
            return "customers/createCustomerForm";
        }
        return "customers/login";
    }

    @GetMapping(value = "/customers/login")
    public String login() {
        return "customers/login";
    }

    @Operation(summary = "고객 로그인")
    @PostMapping(value = "/customers/login")
    public String login(@RequestParam("loginId") String loginId, @RequestParam("password") String password, HttpSession session, Model model) {
        Customer customer = customerService.findByLoginId(loginId);
        if(customer!= null && customer.getPassword().equals(password)) {
            session.setAttribute("customerLoggedOn", customer);
            return "customers/customerHome";
        } else {
            model.addAttribute("error",true);
            return "customers/login";
        }
    }

    @GetMapping(value = "/customers/logout")
    public String logout(HttpSession session) {
        session.invalidate();
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
