package com.shop.concurrency.order.repository;

import com.shop.concurrency.order.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {

    Orders findById(Long id);
}
