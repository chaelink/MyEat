package MyEat.myeat.service;

import MyEat.myeat.domain.ContractStatus;
import MyEat.myeat.domain.Rider;
import MyEat.myeat.repository.RiderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RiderService {
    private final RiderRepository riderRepository;

    @Transactional
    public Long join(Rider rider) {
        validateDuplicateRider(rider);
        riderRepository.save(rider);
        return rider.getId();
    }

    private void validateDuplicateRider(Rider rider) {
        List<Rider> findRiders = riderRepository.findByLoginId(rider.getLoginId());
        if(findRiders.contains(rider)) {
            throw new IllegalStateException("이미 존재하는 아이디입니다");
        }
    }

    public Rider findOne(Long id) {
        return riderRepository.findOne(id);
    }

    public List<Rider> findContractYet() {
        return riderRepository.findContractYet();
    }

    public void contractRider(Long id) {
        Rider rider = findOne(id);
        rider.setStatus(ContractStatus.ON);
    }


}
