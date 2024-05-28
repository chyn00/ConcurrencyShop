package com.shop.concurrency.item.repository;

import com.shop.concurrency.item.model.domain.Item;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    Item saveAndFlush(Item item);

    Item findById(Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select i from item i where i.id = :id")
    Item findByIdWithPessimisticLock(Long id);
}
