package MyEat.myeat.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class OrderMenu {
    @Id @GeneratedValue
    @Column(name = "ordermenu_id")
    private Long id;

    @JoinColumn(name = "order_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    private Long count;

    private Long menuPrice;
}
