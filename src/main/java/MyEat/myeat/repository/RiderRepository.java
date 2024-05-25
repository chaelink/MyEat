package MyEat.myeat.repository;

import MyEat.myeat.domain.Rider;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RiderRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Rider rider) {
        em.persist(rider);
    }

    public List<Rider> findByLoginId(String loginid) {
        return em.createQuery("select m from Rider m where m.loginId=loginId", Rider.class).getResultList();
    }

}
