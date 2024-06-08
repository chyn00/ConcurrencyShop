package com.shop.concurrency.item.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemResponse {
    private Long itemId;

    private Long itemCode;

    private int quantity;

    public boolean isZero() {
        if (this.quantity == 0) {
            return true;
        } else {
            return false;
        }
    }
}
