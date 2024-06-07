package MyEat.myeat.repository;

import MyEat.myeat.domain.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Customer customer) {
        em.persist(customer);
    }

    public Customer findOne(Long id) {
        return em.find(Customer.class, id);
    }

//    public Customer findByLoginId(String loginId) {
//        return em.find(Customer.class,loginId);
//
//        //return em.createQuery("select m from Customer m where m.loginId=loginId",Customer.class);
//    }

    public Customer findByLoginId(String loginId) {
        TypedQuery<Customer> query = em.createQuery("SELECT c FROM Customer c WHERE c.loginId = :loginId", Customer.class);
        query.setParameter("loginId", loginId);
        List<Customer> results = query.getResultList();
        if (results.isEmpty()) {
            return null;
        } else {
            return results.get(0);
        }
    }

    public List<Customer> findAll() {
        return em.createQuery("select m from Customer m",Customer.class)
                .getResultList();
    }
}
