package com.shop.concurrency.order.service;

import com.shop.concurrency.item.service.ItemAtomicService;
import com.shop.concurrency.item.service.ItemService;
import com.shop.concurrency.member.service.MemberService;
import java.util.concurrent.atomic.AtomicInteger;
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

    @Autowired
    ItemAtomicService itemAtomicService;

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


    @Test
    @DisplayName("Atomic을 사용한 테스트")
    void concurrencyTransactionalOrderItemWithAtomicTest() throws InterruptedException {
        AtomicInteger itemOriginQuantity = itemAtomicService.getAtomicItemQuantity(1L);

        ExecutorService executorService = Executors.newFixedThreadPool(6);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    itemAtomicService.decreaseOneItemAtomicQuantity(1L);
                } finally {
                    latch.countDown();
                }
            });
        }

        //병렬 쓰레드를 기다리고 출력하도록
        latch.await();

        /**
        * Atomic 클래스 자체가 Entity가 되는 것은 하이버네이트의 동작 방식과 별개이기 때문에, 적합하지 않다.
         * 차라리, 트랜잭션과 DB에서의 동시성 제어를 사용하는 것이 좋다.(더 공부 필요.)
        * */
        assertNotEquals(itemOriginQuantity.get() - threadCount, itemAtomicService.getAtomicItemQuantity(1L).get());
    }
}