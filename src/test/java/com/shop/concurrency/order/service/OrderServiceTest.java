package com.shop.concurrency.order.service;

import com.shop.concurrency.item.service.ItemService;
import com.shop.concurrency.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
        orderService.synchronizedOrderItemsByMember(1L, 1L);

        System.out.println("재고 일반 접근 1개 - : " + itemService.getItemQuantity(1L));
    }

    @Test
    @DisplayName("orderItemsTest")
    void orderItems() {
        for (int i = 0; i < threadCount; i++) {
            orderService.synchronizedOrderItemsByMember(1L, 1L);
        }
        System.out.println("재고 일반 접근 100개 -: " + itemService.getItemQuantity(1L));
    }

    @Test
    @DisplayName("concurrencySynchronizedOrderItemTest")
    void concurrencySynchronizedOrderItemTest() throws InterruptedException {
        int itemOriginQuantity = itemService.getItemQuantity(1L);
        ExecutorService executorService = Executors.newFixedThreadPool(6);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    orderService.synchronizedOrderItemsByMember(1L, 1L);
                } finally {
                    latch.countDown();
                }
            });
        }

        //병렬 쓰레드를 기다리고 출력하도록
        latch.await();

        assertEquals(itemOriginQuantity - threadCount, itemService.getItemQuantity(1L));
    }


    @Test
    @DisplayName("concurrencyTransactionalOrderItemTest")
    void concurrencyTransactionalOrderItemTest() throws InterruptedException {
        int itemOriginQuantity = itemService.getItemQuantity(1L);
        ExecutorService executorService = Executors.newFixedThreadPool(6);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    orderService.transactionalOrderItemsByMember(1L, 1L);
                } finally {
                    latch.countDown();
                }
            });
        }

        //병렬 쓰레드를 기다리고 출력하도록
        latch.await();

        assertNotEquals(itemOriginQuantity - threadCount, itemService.getItemQuantity(1L));
    }
}