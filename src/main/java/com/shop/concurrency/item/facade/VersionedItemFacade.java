package com.shop.concurrency.item.facade;

import com.shop.concurrency.item.service.VersionedItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VersionedItemFacade {

    private final VersionedItemService versionedItemService;
    public void decreaseItemStockWithOptimisticLock(Long itemId, int quantity) throws InterruptedException {

        int count = 0;
        while(true) {
            try{
                versionedItemService.decreaseItemStockWithOptimisticLock(itemId, quantity);
                break;
            } catch(Exception e) {
                count++;
                System.out.println("update failed count : " + count + " Thread name : " + Thread.currentThread().getName());
                Thread.sleep(50);
            }
        }
    }
}
