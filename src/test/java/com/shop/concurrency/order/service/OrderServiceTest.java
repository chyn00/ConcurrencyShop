package com.shop.concurrency.order.service;

import com.shop.concurrency.item.service.ItemAtomicService;
import com.shop.concurrency.item.service.ItemService;
import com.shop.concurrency.item.service.VersionedItemService;
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

    @Autowired
    VersionedItemService versionedItemService;

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

    @Test
    @DisplayName("Pessimistic Lock을 사용한 테스트")
    void concurrencyOrderItemWithPessimisticLockTest() throws InterruptedException {

        /* given */
        int itemOriginQuantity = itemService.getItemQuantity(1L);
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        CountDownLatch latch = new CountDownLatch(threadCount);

        /* when */
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    orderService.transactionalOrderItemsUsingPessimisticLock(1L, 1L);
                } finally {
                    latch.countDown();
                }
            });
        }

        //병렬 쓰레드를 기다리고 출력하도록
        latch.await();

        /* then */
        int resultStock = itemService.getItemQuantity(1L);
        assertEquals(itemOriginQuantity - threadCount, resultStock);

        /*
         * 비관적 락. Repetable Read 또는 Serializable 정도의 격리성 수준을 제공.
         * race condition이 발생할 여지가 많을수록, 비관적 락 사용시 좋은 성능을 기대할 수 있음.
         * 트랜잭션이 시작될 때 Shared Lock 또는 Exclusive Lock을 걸고 시작한다.*/
    }

    @Test
    @DisplayName("Optimistic Lock을 사용한 테스트")
    void concurrencyOrderItemWithOptimisticLockTest() throws InterruptedException {

        /* given */
        int itemOriginQuantity = versionedItemService.getItemQuantity(1L);
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        /* when */
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    orderService.transactionalOrderItemUsingOptimisticLock(1L, 1L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    latch.countDown();
                }
            });
        }

        //병렬 쓰레드를 기다리고 출력하도록
        latch.await();

        /* then */
        int resultStock = versionedItemService.getItemQuantity(1L);
        assertEquals(itemOriginQuantity - threadCount, resultStock);

        /*
         * 낙관적 락. 자원 자체에 Lock을 걸지 않아 비관적 락 보다 성능이 대체로 좋은 편.
         * race condition이 빈번하게 발생하는 경우라면, 오히려 비관적 락 보다 성능이 좋지 않음.
         * 낙관적 락 update 실패 시 예외 던지지 않고, 0개의 row update 처리하기 때문에 app단에서 사후 처리 필요.
         * orderService에서 @Transactional 적용 시, 같은 쓰레드가 같은 아이템을 계속 가져오는 데드락이 발생.
         * 위의 이유 및 추상화를 위해 facade 클래스에서 update 실패 사후처리 */
    }
}