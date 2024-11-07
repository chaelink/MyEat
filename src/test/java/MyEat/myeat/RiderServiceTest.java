package MyEat.myeat;
import MyEat.myeat.domain.Customer;
import MyEat.myeat.domain.Rider;
import MyEat.myeat.repository.RiderRepository;
import MyEat.myeat.service.RiderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class RiderServiceTest {

    @Mock
    private RiderRepository riderRepository;

    @InjectMocks
    private RiderService riderService;

    private double userLat = 37.5665; // 사용자 위도
    private double userLon = 126.9780; // 사용자 경도

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindContractYet() {
        // 라이더와 고객 설정
        Rider rider1 = new Rider();
        rider1.setId(1L);
        rider1.setName("Rider 1");
        rider1.setCustomerList(Arrays.asList(
                new Customer(1L, 30.5651, 116.9895),  // 먼 고객
                new Customer(2L, 30.5643, 116.9823)   // 먼 고객
        ));

        Rider rider2 = new Rider();
        rider2.setId(2L);
        rider2.setName("Rider 2");
        rider2.setCustomerList(Arrays.asList(
                new Customer(3L, 37.5700, 126.9921),  // 가까운 고객
                new Customer(4L, 37.5720, 126.9931)   // 가까운 고객
        ));

        // RiderRepository에서 반환할 계약되지 않은 라이더 리스트 설정
        when(riderRepository.findContractYet()).thenReturn(Arrays.asList(rider1, rider2));

        // 테스트 실행
        List<Rider> sortedRiders = riderService.findContractYet(userLat, userLon);

        // 검증
        assertEquals(2, sortedRiders.size());
        //assertEquals("Rider 1", sortedRiders.get(0).getName());
        //assertEquals("Rider 2", sortedRiders.get(1).getName());
        System.out.println(sortedRiders.get(0).getName());
        System.out.println(sortedRiders.get(1).getName());
    }
}
