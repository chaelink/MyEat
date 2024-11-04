package MyEat.myeat.service;

import MyEat.myeat.domain.ContractStatus;
import MyEat.myeat.domain.Customer;
import MyEat.myeat.domain.Rider;
import MyEat.myeat.repository.CustomerRepository;
import MyEat.myeat.repository.RiderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractService {

    @Autowired
    private RiderRepository riderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public void contract(Long riderId, Long CustomerId) {
        Rider rider = riderRepository.findOne(riderId);
        Customer customer = customerRepository.findOne(CustomerId);

        rider.setContractCount(rider.getContractCount() + 1);
        rider.getCustomerList().add(customer);
        if (rider.getContractCount() == 5) {
            rider.setContractStatus(ContractStatus.ON);
        }

        customer.setContractStatus(ContractStatus.ON);
        customer.setRider(rider);

    }
}
