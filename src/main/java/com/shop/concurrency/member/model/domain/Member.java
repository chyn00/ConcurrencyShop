package com.shop.concurrency.member.model.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shop.concurrency.order.domain.Orders;
import jakarta.persistence.*;
import jakarta.persistence.criteria.Order;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "member")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @JsonIgnore
    private String name;

    @JsonBackReference
    @OneToMany(mappedBy = "member")// 1 : N 연관관계의 주인이 member이다.
    final List<Orders> orders = new ArrayList<>(); //1(Member) : N(Orders) -> 나는 1개고, order는 여러개다.

}
