package MyEat.myeat.service;

import MyEat.myeat.domain.ContractStatus;
import MyEat.myeat.domain.Customer;
import MyEat.myeat.domain.Rider;
import com.github.javafaker.Faker;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class DummyDataGenerator {

    private final CustomerService customerService;
    private final RiderService riderService;
    private final ContractService contractService;
    private final Faker faker = new Faker(new Locale("ko"));
    //String name = faker.name().fullName(); // 한국어 이름
    //String address = faker.address().fullAddress(); // 한국어 주소


    public DummyDataGenerator(CustomerService customerService, RiderService riderService, ContractService contractService) {
        this.customerService = customerService;
        this.riderService = riderService;
        this.contractService = contractService;
    }

    @Transactional
    public void generateCustomersAndRidersWithContracts(int startId, int count) {
        List<Customer> customers = new ArrayList<>();
        List<Rider> riders = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            int id = startId + i;

            // 고객 생성
            Customer customer = new Customer();
            customer.setLoginId("customer" + id);
            customer.setPassword("password" + id);
            customer.setName(faker.name().fullName());
            customer.setAddress(faker.address().fullAddress());
            customer.setPhoneNumber(010L);
            customer.setContractStatus(ContractStatus.OFF);

            // 위도, 경도 설정
            double latitude = Double.parseDouble(faker.address().latitude());
            double longitude = Double.parseDouble(faker.address().longitude());
            customer.setLatitude(latitude);
            customer.setLongitude(longitude);

            // 라이더 생성
            Rider rider = new Rider();
            rider.setLoginId("rider" + id);
            rider.setPassword("password" + id);
            rider.setName(faker.name().fullName());
            rider.setPhoneNumber(010L);
            rider.setIntroduction("Hello, I'm rider " + id);
            rider.setContractStatus(ContractStatus.OFF);
            rider.setContractCount(0L);


            // 고객 및 라이더 저장
            customerService.join(customer);
            riderService.join(rider);

            // 고객과 라이더 계약 체결
            contractService.contract(rider.getId(), customer.getId());

            System.out.println("Created and contracted Customer ID " + customer.getId() + " with Rider ID " + rider.getId());
        }
    }

    @Transactional
    public void generateCustomers(int startId, int count) {
        List<Customer> customers = new ArrayList<>();
        List<Rider> riders = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            int id = startId + i;

            // 고객 생성
            Customer customer = new Customer();
            customer.setLoginId(Integer.toString(id));
            customer.setPassword(Integer.toString(id));
            customer.setName(faker.name().fullName());
            customer.setAddress(faker.address().fullAddress());
            customer.setPhoneNumber(010L);
            customer.setContractStatus(ContractStatus.OFF);

            // 위도, 경도 설정
            double latitude = Double.parseDouble(faker.address().latitude());
            double longitude = Double.parseDouble(faker.address().longitude());
            customer.setLatitude(latitude);
            customer.setLongitude(longitude);


            // 고객 저장
            customerService.join(customer);

            System.out.println("Created " + customer.getId() );
        }
    }


}