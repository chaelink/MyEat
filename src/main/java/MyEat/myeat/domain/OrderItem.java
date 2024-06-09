package MyEat.myeat.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Embeddable
public class OrderItem {

    private int quantity;
    private Long menuPrice;

    public OrderItem() {

    }

    public OrderItem(int quantity, Long menuPrice) {
        this.quantity = quantity;
        this.menuPrice = menuPrice;
    }
}
