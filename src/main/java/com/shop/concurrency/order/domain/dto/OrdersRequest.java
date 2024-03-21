package com.shop.concurrency.order.domain.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrdersRequest {

    private Long memberId;

    private Long itemId;
}
