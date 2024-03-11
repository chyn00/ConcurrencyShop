package com.shop.concurrency.item.service;

import com.shop.concurrency.item.model.domain.Item;
import com.shop.concurrency.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Item findItemById(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(() -> new NoSuchElementException());
    }
}
