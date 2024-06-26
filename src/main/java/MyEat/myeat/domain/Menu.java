package MyEat.myeat.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Menu {

    @Id @GeneratedValue
    @Column(name = "menu_id")
    private Long id;

    @JoinColumn(name = "restaurant_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Restaurant restaurant;

    private String menuName;
    private Long menuPrice;
}
