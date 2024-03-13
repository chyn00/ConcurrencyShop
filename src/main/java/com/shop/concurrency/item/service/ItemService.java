package com.shop.concurrency.item.service;

import com.shop.concurrency.item.model.domain.Item;
import com.shop.concurrency.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public void makeItemWithCodeAndQuantity(long itemCode, int quantity){
        Item item= Item.builder().itemCode(itemCode).quantity(quantity).build();
        itemRepository.save(item);
    }

    public void decreaseItemQuantityMinusOne(Long itemId) {
        Item item = itemRepository.findById(itemId);
        item.decreaseItemQuantity(1);

        // 수정인 경우 수정 반영
        itemRepository.save(item);
    }
}
