package MyEat.myeat.service;

import MyEat.myeat.domain.*;
import MyEat.myeat.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MenuService menuService;

    public void save(OrderDelivery orderDelivery) {
        orderRepository.save(orderDelivery);
    }

    public void orderDelivery(Map<String, String> menuQuantities, Restaurant restaurant, Customer customer) {
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

        save(orderDelivery);
    }
}
