package MyEat.myeat.repository;

import MyEat.myeat.domain.ContractStatus;
import MyEat.myeat.domain.Rider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RiderJPARepository extends JpaRepository<Rider, Long> {
    Page<Rider> findByContractStatus(ContractStatus contractStatus, Pageable pageable);
}
