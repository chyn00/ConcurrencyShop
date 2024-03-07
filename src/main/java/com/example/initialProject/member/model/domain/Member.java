package com.example.initialProject.member.model.domain;

import com.example.initialProject.order.domain.Orders;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    //1(Member) : N(Orders) -> 나는 1개고, order는 여러개다.
    @OneToMany(mappedBy = "member")// 1 : N 연관관계의 주인이 member이다.
    List<Orders> orders = new ArrayList<>();

}
