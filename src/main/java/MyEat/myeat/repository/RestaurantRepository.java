package MyEat.myeat.repository;

import MyEat.myeat.domain.Restaurant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class RestaurantRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Restaurant restaurant) {
        em.persist(restaurant);
    }

}
