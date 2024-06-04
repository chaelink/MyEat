package MyEat.myeat.repository;

import MyEat.myeat.domain.Customer;
import MyEat.myeat.domain.OrderDelivery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

public class OrderRepository {

    @PersistenceContext
    private EntityManager em;


    public void save(OrderDelivery orderDelivery) {
        em.persist(orderDelivery);
    }

    public List<OrderDelivery> findByCustomerId(Customer customer) {
        return em.createQuery("select m from OrderDelivery m where m.customer.id = customer.id ", OrderDelivery.class).getResultList();
    }

}
