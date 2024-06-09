package MyEat.myeat.repository;

import MyEat.myeat.domain.Customer;
import MyEat.myeat.domain.Restaurant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RestaurantRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Restaurant restaurant) {
        em.persist(restaurant);
    }

    public Restaurant findById(Long id) {
        return em.find(Restaurant.class, id);
    }

    public Restaurant findByLoginId(String loginId) {
        TypedQuery<Restaurant> query = em.createQuery("SELECT c FROM Restaurant c WHERE c.loginId = :loginId", Restaurant.class);
        query.setParameter("loginId", loginId);
        List<Restaurant> results = query.getResultList();
        if (results.isEmpty()) {
            return null;
        } else {
            return results.get(0);
        }
    }

    public List<Restaurant> findAll() {
        return em.createQuery("SELECT c FROM Restaurant c", Restaurant.class).getResultList();
    }


}
