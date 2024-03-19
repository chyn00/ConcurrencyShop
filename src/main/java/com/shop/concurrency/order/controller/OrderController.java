package com.shop.concurrency.order.controller;

import com.shop.concurrency.item.service.ItemService;
import com.shop.concurrency.member.model.domain.Member;
import com.shop.concurrency.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ItemService itemService;

    /**
     * Shop 회원의 Item 주문(회원의 상품 구매)
     */
    @PostMapping("/orders/items/{itemId}")
    public boolean makeItemOrdersByMember(@PathVariable("itemId") Long itemId,@RequestBody Member member) {

        return orderService.synchronizedOrderItemsByMember(itemId, member);
    }

}
