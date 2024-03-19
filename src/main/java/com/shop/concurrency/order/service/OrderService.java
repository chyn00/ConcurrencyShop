package com.shop.concurrency.order.service;

import com.shop.concurrency.item.service.ItemService;
import com.shop.concurrency.member.model.domain.Member;
import com.shop.concurrency.member.service.MemberService;
import com.shop.concurrency.order.domain.Orders;
import com.shop.concurrency.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final MemberService memberService;
    private final ItemService itemService;
    private final OrderRepository orderRepository;

    //    @Transactional
    public synchronized boolean orderItemsByMember(Long itemId, Member member) {

        Orders order = Orders.builder().member(member).build();
        memberService.addOrder(member, order);
        orderRepository.saveAndFlush(order);
        itemService.decreaseOneItemQuantity(itemId, 1);

        return true;
    }
}
