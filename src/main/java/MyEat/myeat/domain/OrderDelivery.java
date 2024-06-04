package MyEat.myeat.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter @Setter
@Entity
public class OrderDelivery {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @JoinColumn(name = "customer_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @JoinColumn(name = "restaurant_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;

    @OneToMany(mappedBy = "orderDelivery")
    private List<OrderMenu> orderMenus = new ArrayList<>();

    private Long totalPrice ;

    private Date date;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @JoinColumn(name = "rider_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Rider rider;
}
