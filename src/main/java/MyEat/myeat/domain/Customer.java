package MyEat.myeat.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private String detailedAddress;
    private Double latitude;  //위도
    private Double longitude; //경도

    private Long phoneNumber;

    @OneToMany(mappedBy = "customer")
    @JsonManagedReference
    private List<OrderDelivery> orderDeliveryList = new ArrayList<OrderDelivery>();

    @Enumerated(EnumType.STRING)
    private ContractStatus contractStatus;

    private Long point;


}
