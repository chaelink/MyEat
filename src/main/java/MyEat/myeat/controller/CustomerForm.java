package MyEat.myeat.controller;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CustomerForm {

    private String loginId;
    private String password;
    private String name;
    private String address;
    private String detailedAddress;
    private Double latitude;  //위도
    private Double longitude; //경도
    private Long phoneNumber;

}
