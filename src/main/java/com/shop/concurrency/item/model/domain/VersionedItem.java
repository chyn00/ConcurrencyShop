package com.shop.concurrency.item.model.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

@Entity(name = "versioned_item")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class VersionedItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private Long itemCode;

    // 수량
    private int quantity;

    @Version
    private Long version;


    public void decreaseItemQuantity(int quantity) {
        this.quantity -= quantity;
    }

    public boolean isZero() {
        if (this.quantity == 0) {
            return true;
        } else {
            return false;
        }
    }
}
