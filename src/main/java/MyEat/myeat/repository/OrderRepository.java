package MyEat.myeat.repository;

import MyEat.myeat.domain.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class OrderRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findByLoginId(String loginId) {
        return em.find(Order.class, loginId);
    }
}
