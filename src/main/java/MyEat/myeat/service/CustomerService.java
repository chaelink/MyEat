package MyEat.myeat.service;

import MyEat.myeat.domain.ContractStatus;
import MyEat.myeat.domain.Customer;
import MyEat.myeat.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;


    @Transactional
    public Long join(Customer customer) {
        validateDuplicateCustomer(customer);
        customerRepository.save(customer);
        return customer.getId();
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
        customer.setStatus(ContractStatus.ON);
    }

    public Customer findById(Long id) {
        return customerRepository.findOne(id);
    }
}
