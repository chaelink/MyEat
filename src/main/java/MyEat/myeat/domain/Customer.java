package MyEat.myeat.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
public class Customer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    private String loginId;

    private String password;

    private String name;

    private String address;

    private Long phoneNumber;

    @OneToMany(mappedBy = "customer")
    private List<OrderDelivery> orderDeliveryList = new ArrayList<OrderDelivery>();

    @Enumerated(EnumType.STRING)
    private ContractStatus status;

    private Long point;


}
