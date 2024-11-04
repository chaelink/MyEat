package MyEat.myeat.repository;

import MyEat.myeat.domain.ContractStatus;
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

    public Rider findOne(Long id) {
        return em.find(Rider.class, id);
    }

    public List<Rider> findByLoginId(String loginid) {
        return em.createQuery("select m from Rider m where m.loginId=loginId", Rider.class)
                .getResultList();
    }

    public List<Rider> findContractYet() {
        ContractStatus contractYet = ContractStatus.OFF;
        return em.createQuery("select m from Rider m where m.contractStatus= :contractYet", Rider.class)
                .setParameter("contractYet", contractYet)
                .getResultList();
    }

}
