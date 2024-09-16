package MyEat.myeat.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Rider {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rider_id")
    private Long id;

    private String loginId;

    private String password;

    private String name;

    private Long phoneNumber;

    private String introduction;

    @Enumerated(EnumType.STRING)
    private ContractStatus status;

    @OneToMany(mappedBy = "rider")
    @JsonManagedReference
    private List<OrderDelivery>deliveryList = new ArrayList<>();
}
