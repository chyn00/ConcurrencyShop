package com.shop.concurrency.item.model.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "atomic_item")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AtomicItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "atomic_item_id")
    private Long id;

    private Long itemCode;

    // 수량
    private AtomicInteger quantity;

    public void decreaseAtomicItemQuantity() {
        this.quantity.getAndDecrement();
    }

    public boolean isZero() {
        if (this.quantity.get() == 0) {
            return true;
        } else {
            return false;
        }
    }
}