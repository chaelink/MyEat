package MyEat.myeat.controller;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RestaurantForm {

    private String loginId;
    private String password;
    private String name;
    private String address;
    private Long phoneNumber;
}
