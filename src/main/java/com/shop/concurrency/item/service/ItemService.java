package com.shop.concurrency.item.service;

import com.shop.concurrency.item.model.domain.Item;
import com.shop.concurrency.item.repository.ItemRepository;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public void makeItem(int quantity){
        Item item= Item.builder().itemCode(Math.round(Math.random()*100000)).quantity(quantity).build();
        itemRepository.save(item);
    }

    public void decreaseOneItemQuantity(Long itemId, int quantity) {
        Item item = itemRepository.findById(itemId);
        //zero인 경우 종료
        if (item.isZero()) {
            return;
        }
        item.decreaseItemQuantity(quantity);
        // 수정인 경우 수정 반영
        itemRepository.saveAndFlush(item);
    }

    public int getItemQuantity(Long id){
        return itemRepository.findById(id).getQuantity();
    }
}
