package MyEat.myeat.controller;

import MyEat.myeat.domain.*;
import MyEat.myeat.service.CustomerService;
import MyEat.myeat.service.MenuService;
import MyEat.myeat.service.OrderService;
import MyEat.myeat.service.RestaurantService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final RestaurantService restaurantService;
    private final OrderService orderService;

    @PostMapping(value = "/order")
    public String order(HttpSession session, @RequestParam("restaurantId") Long restaurantId,
                        @RequestParam Map<String, String> menuQuantities) {

        if (session.getAttribute("customerLoggedIn") == null) {
            return "customers/login";
        }

        Customer customer = (Customer) session.getAttribute("customerLoggedIn");
        Restaurant restaurant = restaurantService.findById(restaurantId);

        orderService.orderDelivery(menuQuantities, restaurant, customer);

        return "customers/customerHome";
    }

}
