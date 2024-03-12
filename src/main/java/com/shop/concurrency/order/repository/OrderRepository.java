package com.shop.concurrency.order.repository;

import com.shop.concurrency.order.domain.Orders;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {

    Optional<Orders> findById(Integer integer);
}
