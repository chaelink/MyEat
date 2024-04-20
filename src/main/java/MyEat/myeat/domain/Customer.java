package MyEat.myeat.domain;

import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;

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

    @Embedded
    private RabbitConnectionDetails.Address address;

    private Long phoneNumber;

    @OneToMany(mappedBy = "customer")
    private List<Order> orderList = new ArrayList<Order>();

    @Enumerated(EnumType.STRING)
    private ContractStatus status;

}
