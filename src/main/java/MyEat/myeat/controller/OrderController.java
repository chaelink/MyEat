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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final CustomerService customerService;
    private final RestaurantService restaurantService;
    private final OrderService orderService;
    private final MenuService menuService;

    @PostMapping(value = "/order")
    public String order(HttpSession session, @RequestParam("restaurantId") Long restaurantId,
                        @RequestParam Map<String, String> menuQuantities) {

        if (session.getAttribute("customerLoggedIn") == null) {
            return "customers/login";
        }

        Customer customer = (Customer) session.getAttribute("customerLoggedIn");
        Restaurant restaurant = restaurantService.findById(restaurantId);

        Map<Long, OrderItem> orderMenus = new HashMap<>();
        Long totalPrice = 0L;

        for (Map.Entry<String, String> entry : menuQuantities.entrySet()) {
            if (entry.getKey().startsWith("menuQuantities[")) {
                Long menuId = Long.parseLong(entry.getKey().substring(15, entry.getKey().length() - 1));
                int quantity = Integer.parseInt(entry.getValue());

                if (quantity > 0) {
                    Menu menu = menuService.findById(menuId); // 메뉴 정보를 DB에서 가져옴
                    Long menuPrice = menu.getMenuPrice();
                    orderMenus.put(menuId, new OrderItem(quantity, menuPrice));
                    totalPrice += menuPrice * quantity;
                }
            }
        }

        OrderDelivery orderDelivery = new OrderDelivery();
        orderDelivery.setCustomer(customer);
        orderDelivery.setRestaurant(restaurant);
        orderDelivery.setOrderMenus(orderMenus);
        orderDelivery.setTotalPrice(totalPrice);
        orderDelivery.setDate(new Date());
        orderDelivery.setOrderStatus(OrderStatus.INPROGRESS);

        orderService.save(orderDelivery);

        return "customers/customerHome";
    }

}
