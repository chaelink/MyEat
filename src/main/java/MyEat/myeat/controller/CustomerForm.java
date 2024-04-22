package MyEat.myeat.controller;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CustomerForm {

    private String loginId;
    private String password;
    private String name;
    private String address;
    private Long phoneNumber;

}
