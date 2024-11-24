package MyEat.myeat.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class MenuDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String menuName;
    private Long menuPrice;
}