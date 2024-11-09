package MyEat.myeat.service;

import MyEat.myeat.controller.RiderForm;
import MyEat.myeat.domain.ContractStatus;
import MyEat.myeat.domain.Customer;
import MyEat.myeat.domain.Rider;
import MyEat.myeat.repository.RiderJPARepository;
import MyEat.myeat.repository.RiderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
@RequiredArgsConstructor
public class RiderService {

    private final RiderRepository riderRepository;
    private final RiderJPARepository riderJPARepository;
    private final PasswordEncoder passwordEncoder;

    private static final double EARTH_RADIUS = 6371; // 지구 반경 (단위: km)

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
        rider.setContractStatus(ContractStatus.OFF);
        rider.setContractCount(0L);

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


    public List<Rider> findContractYetnorecommend() {
        ContractStatus contractYet = ContractStatus.OFF;
        return riderRepository.findContractYet();
    }


    //=========================================추천 알고리즘
    // Rider와 거리를 묶어 정렬을 위한 클래스
    private static class RiderDistance {
        private final Rider rider;
        private final double distance;

        public RiderDistance(Rider rider, double distance) {
            this.rider = rider;
            this.distance = distance;
        }

        public Rider getRider() {
            return rider;
        }

        public double getDistance() {
            return distance;
        }
    }

    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c; // 결과 거리 km
    }

    public List<Rider> findContractYet(double userLat, double userLon) {
        List<Rider> riders = riderRepository.findContractYet();
        //라이더의 고객리스트 내부 고객과 나와의 거리를 구해서 평균을 낸 점수와 라이더 저장
        //모든 라이더에 대한 계산을 마친 후 점수가 낮은 순서(거리가 가까운 순서)대로 라이더리스트 정렬해서 return
        // 각 라이더에 대해 고객과 사용자 간의 평균 거리 계산
        List<RiderDistance> riderDistances = new ArrayList<>();
        for (Rider rider : riders) {
            List<Customer> customers = rider.getCustomerList();

            // 고객들과의 거리 합산 및 평균 계산
            double totalDistance = 0;
            int count = 0;

            for (Customer customer : customers) {
                double distance = calculateDistance(
                        userLat, userLon,
                        customer.getLatitude(), customer.getLongitude()
                );
                totalDistance += distance;
                count++;
            }

            // 평균 거리 계산
            double averageDistance = (count > 0) ? (totalDistance / count) : 0;

            // 라이더와 평균 거리 저장
            riderDistances.add(new RiderDistance(rider, averageDistance));
            System.out.println(rider.getId());
            System.out.println(averageDistance);
        }

        // 평균 거리 기준으로 정렬 (가까운 순서대로)
        riderDistances.sort(Comparator.comparingDouble(RiderDistance::getDistance));

        // 정렬된 라이더 리스트 반환
        List<Rider> sortedRiders = new ArrayList<>();
        for (RiderDistance riderDistance : riderDistances) {
            sortedRiders.add(riderDistance.getRider());
        }

        return sortedRiders;
    }

    @Async
    public CompletableFuture<List<Rider>> findContractYetAsync(double userLat, double userLon) {
        List<Rider> sortedRiders = findContractYet(userLat, userLon);
        return CompletableFuture.completedFuture(sortedRiders);
    }
}






