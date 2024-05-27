package com.shop.concurrency.item.repository;

import com.shop.concurrency.item.model.domain.Item;
import com.shop.concurrency.item.model.domain.VersionedItem;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VersionedItemRepository extends JpaRepository<VersionedItem, Integer> {

    VersionedItem saveAndFlush(VersionedItem item);

    VersionedItem findById(Long id);

    @Lock(LockModeType.OPTIMISTIC)
    @Query("select i from versioned_item i where i.id = :id")
    VersionedItem findByIdWithOptimisticLock(Long id);
}
