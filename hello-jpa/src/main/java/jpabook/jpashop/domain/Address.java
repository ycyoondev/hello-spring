package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter // 값 타입은 변경하지 않는다.
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() {
    } // 생성자에서 값을 초기화 시켜 변경 불가능한 클래스화

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
