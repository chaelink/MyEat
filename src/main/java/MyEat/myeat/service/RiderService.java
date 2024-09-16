package MyEat.myeat.service;

import MyEat.myeat.controller.RiderForm;
import MyEat.myeat.domain.ContractStatus;
import MyEat.myeat.domain.Rider;
import MyEat.myeat.repository.RiderJPARepository;
import MyEat.myeat.repository.RiderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RiderService {

    private final RiderRepository riderRepository;
    private final RiderJPARepository riderJPARepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void join(Rider rider) {
        validateDuplicateRider(rider);
        rider.setPassword(passwordEncoder.encode(rider.getPassword()));
        riderRepository.save(rider);

    }

    public Rider createRiderFromForm(RiderForm form) {
        Rider rider = new Rider();
        rider.setLoginId(form.getLoginId());
        rider.setPassword(form.getPassword());
        rider.setName(form.getName());
        rider.setPhoneNumber(form.getPhoneNumber());
        rider.setIntroduction(form.getIntroduction());
        rider.setStatus(ContractStatus.OFF);

        return rider;
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

    public Page<Rider> findContractYet(Pageable pageable) {
        ContractStatus contractYet = ContractStatus.OFF;
        return riderJPARepository.findByStatus(contractYet,pageable);
    }

    public void contractRider(Long id) {
        Rider rider = findOne(id);
        rider.setStatus(ContractStatus.ON);
    }


}
