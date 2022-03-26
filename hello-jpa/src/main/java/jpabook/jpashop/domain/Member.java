package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name="member_id")
    private Long id;
    
    @NotEmpty // validation에서 확인됨 (하지만, Entity에 이렇게 있으면 다양한 활용에 문제가 생김)
    private String name;

    @Embedded // 내장타입을 포함함
    private Address address;

    @OneToMany(mappedBy = "member") // order Table의 member field에 의한 맵핑됨 (읽기로 사용)
    private List<Order> orders = new ArrayList<>();


}
