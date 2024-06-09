package MyEat.myeat.repository;

import MyEat.myeat.domain.Menu;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MenuRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Menu menu) {
        em.persist(menu);
    }

    public Menu findById(Long id) {
        return em.find(Menu.class, id);
    }

}
