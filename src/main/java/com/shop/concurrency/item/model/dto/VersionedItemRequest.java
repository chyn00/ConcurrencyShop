package com.shop.concurrency.item.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class VersionedItemRequest {

    private Long itemId;
    private Long itemCode;
    private int quantity;
    private Long version;

    public VersionedItemRequest(VersionedItemResponse response) {
        this.itemId = response.getItemId();
        this.itemCode = response.getItemCode();
        this.quantity = response.getQuantity();
        this.version = response.getVersion();
    }

    public boolean isZero() {
        if (this.quantity == 0) {
            return true;
        } else {
            return false;
        }
    }
}
