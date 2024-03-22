package com.shop.concurrency.order.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.shop.concurrency.member.model.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "orders")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Orders {// Order는 자바 예약어이기 때문에, orders로 주문을 표시해준다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orders_id")
    private Long id;

    // N:1을 해야 설계 이해가 쉬움, 객체의 책임과 역할
    @ManyToOne//N(Order) : 1(Member) -> 나(주문)는 여러개고, Member는 1개다.
    @JoinColumn(name = "member_id")
    Member member;
}
