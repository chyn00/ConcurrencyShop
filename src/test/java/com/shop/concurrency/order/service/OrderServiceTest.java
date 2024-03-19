package com.shop.concurrency.order.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.shop.concurrency.item.service.ItemService;
import com.shop.concurrency.member.model.domain.Member;
import com.shop.concurrency.member.service.MemberService;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderServiceTest {

    private final int threadCount = 100;

    @Autowired
    OrderService orderService;

    @Autowired
    MemberService memberService;

    @Autowired
    ItemService itemService;

    @Test
    @DisplayName("orderItemTest")
    void orderItemByMember() {
        Member member = Member.builder().id(1L).name("kim").build();
        orderService.synchronizedOrderItemsByMember(1L, member);

        System.out.println("재고 일반 접근 1개 - : " + itemService.getItemQuantity(1L));
    }

    @Test
    @DisplayName("orderItemsTest")
    void orderItems() {
        Member member = Member.builder().id(1L).name("kim").build();
        for (int i = 0; i < threadCount; i++) {
            orderService.synchronizedOrderItemsByMember(1L, member);
        }
        System.out.println("재고 일반 접근 100개 -: " + itemService.getItemQuantity(1L));
    }

    @Test
    @DisplayName("concurrencySynchronizedOrderItemTest")
    void concurrencySynchronizedOrderItemTest() throws InterruptedException {
        Member member= memberService.findMember(1L);
        final Member finalMember = member;

        ExecutorService executorService = Executors.newFixedThreadPool(6);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    orderService.synchronizedOrderItemsByMember(1L, finalMember);
                } finally {
                    latch.countDown();
                }
            });
        }

        //병렬 쓰레드를 기다리고 출력하도록
        latch.await();

        assertEquals(900, itemService.getItemQuantity(1L));
    }


    @Test
    @DisplayName("concurrencyTransactionalOrderItemTest")
    void concurrencyTransactionalOrderItemTest() throws InterruptedException {
        Member member= memberService.findMember(1L);
        final Member finalMember = member;

        ExecutorService executorService = Executors.newFixedThreadPool(6);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    orderService.transactionalOrderItemsByMember(1L, finalMember);
                } finally {
                    latch.countDown();
                }
            });
        }

        //병렬 쓰레드를 기다리고 출력하도록
        latch.await();

        assertNotEquals(900, itemService.getItemQuantity(1L));
    }
}