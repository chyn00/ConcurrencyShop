package com.shop.concurrency.item.service;

import com.shop.concurrency.item.model.domain.Item;
import com.shop.concurrency.item.model.domain.VersionedItem;
import com.shop.concurrency.item.repository.ItemRepository;
import com.shop.concurrency.item.repository.VersionedItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VersionedItemService {

    private final VersionedItemRepository versionedItemRepository;

    public void makeItem(int quantity){
        VersionedItem versionedItem = VersionedItem.builder().itemCode(Math.round(Math.random()*100000)).quantity(quantity).build();
        versionedItemRepository.save(versionedItem);
    }
    public void decreaseOneItemQuantity(Long itemId, int quantity) {
        VersionedItem versionedItem = versionedItemRepository.findById(itemId);
        //zero인 경우 종료
        if (versionedItem.isZero()) {
            return;
        }
        versionedItem.decreaseItemQuantity(quantity);
        // 수정인 경우 수정 반영
        versionedItemRepository.saveAndFlush(versionedItem);
    }

    public int getItemQuantity(Long id){
        return versionedItemRepository.findById(id).getQuantity();
    }

    @Transactional
    public void decreaseItemStockWithOptimisticLock(Long itemId, int quantity) throws InterruptedException {
        VersionedItem versionedItem = versionedItemRepository.findByIdWithOptimisticLock(itemId);

        if(versionedItem.isZero()) {
            return;
        }

        versionedItem.decreaseItemQuantity(quantity);
        // 수정인 경우 수정 반영
        versionedItemRepository.saveAndFlush(versionedItem);
    }
}
