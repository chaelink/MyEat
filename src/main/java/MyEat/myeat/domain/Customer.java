package MyEat.myeat.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private double latitude;  //위도
    private double longitude; //경도

    private Long phoneNumber;

    @OneToMany(mappedBy = "customer")
    @JsonManagedReference
    private List<OrderDelivery> orderDeliveryList = new ArrayList<OrderDelivery>();

    @Enumerated(EnumType.STRING)
    private ContractStatus contractStatus;

    @JoinColumn(name="rider_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Rider rider;

    public Customer(Long id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public Customer() {}
}
