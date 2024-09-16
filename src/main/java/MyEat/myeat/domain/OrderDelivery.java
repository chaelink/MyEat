package MyEat.myeat.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter @Setter
@Entity
public class OrderDelivery {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @JoinColumn(name = "customer_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Customer customer;

    @JoinColumn(name = "restaurant_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Restaurant restaurant;

    @ElementCollection
    @CollectionTable(name = "order_menus", joinColumns = @JoinColumn(name = "order_id"))
    @MapKeyColumn(name = "menu_id")
    private Map<Long, OrderItem> orderMenus = new HashMap<>();

    private Long totalPrice ;

    private Date date;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @JoinColumn(name = "rider_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Rider rider;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;
}
