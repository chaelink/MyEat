package MyEat.myeat.service;

import MyEat.myeat.controller.CustomerForm;
import MyEat.myeat.domain.ContractStatus;
import MyEat.myeat.domain.Customer;
import MyEat.myeat.domain.OrderDelivery;
import MyEat.myeat.repository.CustomerRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.plaf.PanelUI;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public void join(Customer customer) {
        validateDuplicateCustomer(customer);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customerRepository.save(customer);
    }

    public Customer createCustomerFromForm(CustomerForm form) {
        Customer customer = new Customer();
        customer.setLoginId(form.getLoginId());
        customer.setPassword(form.getPassword());
        customer.setName(form.getName());
        customer.setAddress(form.getAddress());
        customer.setDetailedAddress(form.getDetailedAddress());
        customer.setLatitude(form.getLatitude());
        customer.setLongitude(form.getLongitude());
        customer.setPhoneNumber(form.getPhoneNumber());
        customer.setContractStatus(ContractStatus.OFF);
        return customer;
    }

    public Customer login(String loginId, String password) {
        Customer customer = findByLoginId(loginId);
        if (customer == null || !passwordEncoder.matches(password, customer.getPassword())) {
            throw new IllegalArgumentException("존재하지 않는 ID이거나 비밀번호가 틀립니다.");
        }
        return customer;
    }

    public void logout(HttpSession session) {
        session.invalidate();
    }

    private void validateDuplicateCustomer(Customer customer) {
        Customer findcustomer = customerRepository.findByLoginId(customer.getLoginId());
        if (findcustomer != null) {
            throw new DuplicateCustomerException("이미 존재하는 아이디입니다");
        }
    }

    //사용자 정의 예외 클래스
    public class DuplicateCustomerException extends RuntimeException {
        public DuplicateCustomerException(String message) {
            super(message);
        }
    }

    public Customer findByLoginId(String loginId) {
        return customerRepository.findByLoginId(loginId);
    }

    public void contractCustomer(Long id) {
        Customer customer = customerRepository.findOne(id);
        customer.setContractStatus(ContractStatus.ON);
    }

    public Customer findById(Long id) {
        return customerRepository.findOne(id);
    }

    public List<OrderDelivery> customerOrderedList(Customer customer) {
        return customer.getOrderDeliveryList();
    }
}
