package com.shop.concurrency.item.service;

import com.shop.concurrency.item.model.domain.AtomicItem;
import com.shop.concurrency.item.repository.AtomicItemRepository;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemAtomicService {

    private final AtomicItemRepository atomicItemRepository;

    public void decreaseOneItemAtomicQuantity(Long itemId) {
        AtomicItem atomicItem = atomicItemRepository.findById(itemId);
        //zero인 경우 종료
        if (atomicItem.isZero()) {
            return;
        }
        atomicItem.decreaseAtomicItemQuantity();
        // 수정인 경우 수정 반영
        atomicItemRepository.saveAndFlush(atomicItem);
    }

    public AtomicInteger getAtomicItemQuantity(Long id){
        return atomicItemRepository.findById(id).getQuantity();
    }

    public void makeAtomicItem(AtomicInteger quantity){
        AtomicItem item= AtomicItem.builder().itemCode(Math.round(Math.random()*100000)).quantity(quantity).build();
        atomicItemRepository.save(item);
    }

}
