package com.shop.concurrency.item.model.dto;

import jakarta.persistence.Version;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class VersionedItemResponse {

    private Long itemId;

    private Long itemCode;

    private int quantity;

    private Long version;

}
