package MyEat.myeat.service;

import MyEat.myeat.domain.OrderDelivery;
import MyEat.myeat.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void save(OrderDelivery orderDelivery) {
        orderRepository.save(orderDelivery);
    }
}
