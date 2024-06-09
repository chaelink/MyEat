package MyEat.myeat.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Restaurant {

    @Id @GeneratedValue
    @Column(name = "restaurant_id")
    private Long id;

    private String loginId;
    private String password;

    private String name;
    private String address;
    private Long phoneNumber;

    @OneToMany(mappedBy = "restaurant")
    private List<Menu>menus =new ArrayList<>();
}
