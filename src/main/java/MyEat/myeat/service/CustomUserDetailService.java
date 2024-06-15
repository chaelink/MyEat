package MyEat.myeat.service;

import MyEat.myeat.domain.Customer;
import MyEat.myeat.repository.CustomerRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    //용도 다시 공부
    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByLoginId(loginId);
        if (customer == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(customer.getLoginId(), customer.getPassword(), new ArrayList<>());
    }
}
