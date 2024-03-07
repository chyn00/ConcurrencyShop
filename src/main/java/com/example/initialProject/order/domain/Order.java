package com.example.initialProject.order.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    //N(Order) : 1(Member) -> 나는 여러개고, Member는 1개다.
    @ManyToOne
    Order orders;
}
