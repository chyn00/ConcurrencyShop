package com.shop.concurrency.order.service;

import com.shop.concurrency.item.service.ItemAtomicService;
import com.shop.concurrency.item.service.ItemService;
import com.shop.concurrency.item.service.VersionedItemService;
import com.shop.concurrency.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class OrderServiceMybatisTest {

    private final int threadCount = 100;

    @Autowired
    OrderService orderService;

    @Autowired
    MemberService memberService;

    @Autowired
    ItemService itemService;

    @Autowired
    VersionedItemService versionedItemService;

    @Test
    @DisplayName("Optimistic Lock을 사용한 테스트 - Mybatis ver")
    void concurrencyOrderItemWithOptimisticLockTest() throws InterruptedException {

        /* given */
        int itemOriginQuantity = versionedItemService.getItemQuantityUsingMybatis(1L);
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        CountDownLatch latch = new CountDownLatch(threadCount);

        /* when */
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    orderService.transactionalOrderUsingOptimisticLockAndMybatis(1L, 1L);
                } finally {
                    latch.countDown();
                }
            });
        }

        //병렬 쓰레드를 기다리고 출력하도록
        latch.await();

        /* then */
        int resultStock = versionedItemService.getItemQuantityUsingMybatis(1L);
        assertEquals(itemOriginQuantity - threadCount, resultStock);
    }

    @Test
    @DisplayName("Pessimistic Lock을 사용한 테스트 - Mybatis ver")
    void concurrencyOrderItemWithPessimisticLockTest() throws InterruptedException {

        /* given */
        int itemOriginQuantity = itemService.getItemQuantitUsingMybatis(1L);
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        CountDownLatch latch = new CountDownLatch(threadCount);

        /* when */
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    orderService.transactionalOrderUsingPessimisticLockAndMybatis(1L, 1L);
                } finally {
                    latch.countDown();
                }
            });
        }

        //병렬 쓰레드를 기다리고 출력하도록
        latch.await();

        /* then */
        int resultStock = itemService.getItemQuantitUsingMybatis(1L);
        assertEquals(itemOriginQuantity - threadCount, resultStock);
    }
}
