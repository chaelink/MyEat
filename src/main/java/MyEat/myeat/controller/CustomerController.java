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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name="Customer API", description = "개인 고객 API") //FOR SWAGGER
@Controller
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final PasswordEncoder passwordEncoder;


    @Hidden
    @GetMapping(value = "/customers/new")  //고객 회원가입 폼
    public String createForm(Model model) {
        model.addAttribute("CustomerForm", new CustomerForm());
        return "customers/createCustomerForm";
    }


    @Operation(summary = "고객 회원가입")
    @PostMapping(value = "/customers/new")  //고객 회원가입
    public String create(@Valid CustomerForm customerForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "customers/createCustomerForm";
        }

        Customer customer = customerService.createCustomerFromForm(customerForm);

        try {
            customerService.join(customer);
        } catch (CustomerService.DuplicateCustomerException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("showAlert",true);
            return "customers/createCustomerForm";
        }
        return "customers/login";
    }

    @Hidden
    @GetMapping(value = "/customers/login")  //고객 로그인 폼
    public String login() {
        return "customers/login";
    }


    @Operation(summary = "고객 로그인")
    @PostMapping(value = "/customers/login")  //고객 로그인
    public String login(@RequestParam("loginId") String loginId, @RequestParam("password") String password,
                        HttpSession session, Model model) {
        try {
            Customer customer = customerService.login(loginId,password);
            session.setAttribute("customerLoggedIn", customer);
            return "customers/customerHome";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "customers/login";
        }
    }

    @PostMapping(value = "/customers/logout")
    public String logout(HttpSession session) {
        customerService.logout(session);
        return "redirect:/";
    }
}
