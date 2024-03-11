package com.shop.concurrency.member.service;

import com.shop.concurrency.item.model.domain.Item;
import com.shop.concurrency.item.repository.ItemRepository;
import com.shop.concurrency.member.model.domain.Member;
import com.shop.concurrency.member.repository.MemberRepository;
import com.shop.concurrency.order.domain.Orders;
import com.shop.concurrency.order.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired OrderService orderService;
    @Autowired ItemRepository itemRepository;
    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long savedId = memberService.join(member);

        //then
        assertEquals(member, memberRepository.findById(savedId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
        memberService.join(member2); //예외가 발생해야 한다!!!

        //then
        fail("예외가 발생해야 한다.");
    }

    @Test
    public void 재고_차감() {

        Member member = Member.builder()
                .id(99L)
                .name("sk")
                .orders(new ArrayList<>())
                .build();
        // 상품 생성
        Item originItem = Item.builder()
                .stock(200L)
                .build();

        itemRepository.save(originItem);
        // 주문 생성, 재고 차감
        orderService.orderItemsByMember(originItem.getId(), member);
        // 업데이트
        itemRepository.save(originItem);
        // 재고 확인
        Item updatedItem = itemRepository.findById(originItem.getId()).orElseThrow();
        assertTrue(originItem.getStock() > updatedItem.getStock());

        // 왜 실패하지??
    }

}