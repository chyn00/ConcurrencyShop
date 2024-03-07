package com.shop.concurrency.order.domain;

import com.shop.concurrency.member.model.domain.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Orders {// Order는 자바 예약어이기 때문에, orders로 주문을 표시해준다.
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    //N(Order) : 1(Member) -> 나는 여러개고, Member는 1개다.
    @ManyToOne
    Member member;
}
