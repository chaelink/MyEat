package MyEat.myeat.service;

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

//    public CustomerService(CustomerRepository customerRepository) {
//        this.customerRepository = customerRepository;
//    }

    @Transactional
    public Long join(Customer customer) {
        validateDuplicateCustomer(customer);
        customerRepository.save(customer);
        return customer.getId();
    }

    private void validateDuplicateCustomer(Customer customer) {
        List<Customer> findCustomers = customerRepository.findByLoginId(customer.getLoginId());
        if (!findCustomers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 아이디입니다");
        }
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
}
