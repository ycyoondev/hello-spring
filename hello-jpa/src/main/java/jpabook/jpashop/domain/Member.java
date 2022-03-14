package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String username;

    @Embedded // 내장타입을 포함함
    private Address address;

    @OneToMany(mappedBy = "member") // order Table의 member field에 의한 맵핑됨 (읽기로 사용)
    private List<Order> orders = new ArrayList<>();


}
