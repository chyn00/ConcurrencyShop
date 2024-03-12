package com.shop.concurrency.item.repository;

import com.shop.concurrency.item.model.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
