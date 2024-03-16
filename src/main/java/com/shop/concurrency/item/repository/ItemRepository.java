package com.shop.concurrency.item.repository;

import com.shop.concurrency.item.model.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    Item saveAndFlush(Item item);

    Item findById(Long id);
}
