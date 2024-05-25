package MyEat.myeat.repository;

import MyEat.myeat.domain.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Customer customer) {
        em.persist(customer);
    }

    public void findOne(Long id) {
        em.find(Customer.class, id);
    }

    public List<Customer> findByLoginId(String loginId) {
        return em.createQuery("select m from Customer m where m.loginId=loginId",Customer.class)
                .getResultList();
    }

    public List<Customer> findAll() {
        return em.createQuery("select m from Customer m",Customer.class)
                .getResultList();
    }
}
