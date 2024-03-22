package com.shop.concurrency.order.controller;

import com.shop.concurrency.order.domain.dto.OrdersRequest;
import com.shop.concurrency.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * Shop 회원의 Item 주문(회원의 상품 구매)
     */
    @PostMapping("/orders/items")
    public boolean makeItemOrdersByMember(@RequestBody OrdersRequest ordersRequest) {

        return orderService.synchronizedOrderItemsByMember(ordersRequest.getItemId(), ordersRequest.getMemberId());
    }

}
