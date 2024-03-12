package com.shop.concurrency.order.service;

import com.shop.concurrency.item.model.domain.Item;
import com.shop.concurrency.item.service.ItemService;
import com.shop.concurrency.member.model.domain.Member;
import com.shop.concurrency.order.domain.Orders;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ItemService itemService;

    public boolean orderItemsByMember(Long itemId, Member member) {

        Orders order = Orders.builder().build();
        member.getOrders().add(order);

        //TODO: Item decrease 작성, 독립적으로 가능

        deductStock(itemId);
        return true;
    }

    private void deductStock(Long itemId) {
        Item item = itemService.findItemById(itemId);
        item.decreaseStock(-1);
    }
}
