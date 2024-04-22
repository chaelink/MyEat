package MyEat.myeat.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
public class Customer {

    @Id @GeneratedValue
    @Column(name = "customer_id")
    private Long id;

    private String loginId;

    private String password;

    private String name;

    private String address;

    private Long phoneNumber;

    @OneToMany(mappedBy = "customer")
    private List<Order> orderList = new ArrayList<Order>();

    @Enumerated(EnumType.STRING)
    private ContractStatus status;

}
