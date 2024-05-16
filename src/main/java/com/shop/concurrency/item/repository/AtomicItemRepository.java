package com.shop.concurrency.item.repository;

import com.shop.concurrency.item.model.domain.AtomicItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtomicItemRepository extends JpaRepository<AtomicItem, Integer> {

    AtomicItem saveAndFlush(AtomicItem item);

    AtomicItem findById(Long id);
}
