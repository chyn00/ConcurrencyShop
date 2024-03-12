package com.shop.concurrency.order.service;

import com.shop.concurrency.item.service.ItemService;
import com.shop.concurrency.member.model.domain.Member;
import com.shop.concurrency.member.service.MemberService;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @Autowired
    MemberService memberService;

    @Autowired
    ItemService itemService;

    @BeforeEach
    public void createMember(){
        List<Member> members = memberService.findMembers();
        Member member = Member.builder().name("kim").build();
        boolean result = members.stream().anyMatch(it -> it.getName().equals("kim"));
        itemService.makeItem(10);

        if(!result){
            memberService.join(member);
        }
    }

    @Test
    @DisplayName("orderItemTest")
    void orderItemsByMember() {
        Member member = Member.builder().id(1L).name("kim").build();
        orderService.orderItemsByMember(1L, member);
    }
}