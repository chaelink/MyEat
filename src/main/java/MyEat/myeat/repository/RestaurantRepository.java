package MyEat.myeat.repository;

import MyEat.myeat.domain.Restaurant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RestaurantRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Restaurant restaurant) {
        em.persist(restaurant);
    }

    public List<Restaurant> findByLoginId(String loginId) {
        return em.createQuery("select m from Restaurant m where m.loginId = loginId", Restaurant.class)
                .getResultList();
    }


}
