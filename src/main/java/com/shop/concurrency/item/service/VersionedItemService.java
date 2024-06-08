package com.shop.concurrency.item.service;

import com.shop.concurrency.common.exception.filter.constant.ExceptionCodeEnum;
import com.shop.concurrency.common.exception.filter.model.common.ExceptionObject;
import com.shop.concurrency.item.model.domain.Item;
import com.shop.concurrency.item.model.domain.VersionedItem;
import com.shop.concurrency.item.model.dto.ItemResponse;
import com.shop.concurrency.item.model.dto.VersionedItemRequest;
import com.shop.concurrency.item.model.dto.VersionedItemResponse;
import com.shop.concurrency.item.repository.ItemRepository;
import com.shop.concurrency.item.repository.VersionedItemRepository;
import com.shop.concurrency.item.repository.mapper.ItemMapper;
import com.shop.concurrency.item.repository.mapper.VersionedItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class VersionedItemService {

    private final VersionedItemRepository versionedItemRepository;
    private final VersionedItemMapper versionedItemMapper;
    private final ItemMapper itemMapper;

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

    public int getItemQuantityUsingMybatis(Long itemId) {
        return versionedItemMapper.getItemByIdAndVersion(itemId).getQuantity();
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

    public void decreaseItemStockWithMybatisOptimisticLock(Long itemId, int quantity) {

        VersionedItemResponse verItem = versionedItemMapper.getItemByIdAndVersion(itemId);
        VersionedItemRequest verReq = new VersionedItemRequest(verItem);
        verReq.setQuantity(verReq.getQuantity() - quantity);

        if(verReq.isZero()) {
            return;
        }

        int cnt = 1;

        for(int i=0; i<100; i++) {
            try {
                updateOrderItem(verReq);
                return;
            } catch(Exception e) {
                System.out.println("Fail to update || Retry count : " + cnt++ + " || Current version : " + verItem.getVersion() + " || Current Thread : " + Thread.currentThread());
                verItem = getVersionedItem(itemId);
                verReq = new VersionedItemRequest(verItem);
                verReq.setQuantity(verReq.getQuantity() - quantity);
            }
        }
        throw new ExceptionObject(ExceptionCodeEnum.ItemOptimisticLockException);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    protected VersionedItemResponse getVersionedItem(Long itemId) {
        return versionedItemMapper.getItemByIdAndVersion(itemId);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    protected void updateOrderItem(VersionedItemRequest request) {
        int updatedRows = versionedItemMapper.updateItem(request);
        if(updatedRows == 0) {
            throw new OptimisticLockingFailureException("Version mismatch");
        }
    }
}
