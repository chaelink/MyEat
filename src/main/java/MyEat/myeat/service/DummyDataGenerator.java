package MyEat.myeat.service;

import MyEat.myeat.domain.*;
import com.github.javafaker.Faker;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
public class DummyDataGenerator {

    private final CustomerService customerService;
    private final RiderService riderService;
    private final ContractService contractService;
    private final RestaurantService restaurantService;
    private final MenuService menuService;
    private final Faker faker = new Faker(new Locale("ko"));


    public DummyDataGenerator(CustomerService customerService, RiderService riderService, ContractService contractService, RestaurantService restaurantService, MenuService menuService) {
        this.customerService = customerService;
        this.riderService = riderService;
        this.contractService = contractService;
        this.restaurantService = restaurantService;
        this.menuService = menuService;
    }

    @Transactional
    public void generateCustomersAndRidersWithContracts(int startId, int count) {
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


    public void generateRestaurant() {
        List<String> menuNames = Arrays.asList("된장찌개","김치찌개","비지찌개", "고등어구이","참치", "공깃밥", "음료", "커피", "김치", "물");
        List<Long> menuPrices = Arrays.asList(10000L, 10000L, 20000L, 20000L, 5000L, 5000L, 5000L, 5000L, 5000L, 5000L);

        for(int i=1; i<=981; i++) {
            Restaurant restaurant = new Restaurant();
            restaurant.setLoginId(faker.name().username());
            restaurant.setPassword("password");
            restaurant.setName(faker.company().name());
            restaurant.setAddress(faker.address().fullAddress());
            restaurant.setPhoneNumber(faker.number().randomNumber(10, true));
            restaurantService.join(restaurant);

            for (int j=0; j<menuNames.size(); j++) {
                Menu menu = new Menu();
                menu.setMenuName(menuNames.get(j));
                menu.setMenuPrice(menuPrices.get(j));
                menu.setRestaurant(restaurant);
                menuService.save(menu);
                restaurant.getMenus().add(menu);
            }

        }
    }


}
