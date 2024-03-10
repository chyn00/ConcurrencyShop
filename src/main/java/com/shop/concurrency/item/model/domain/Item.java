package com.shop.concurrency.item.model.domain;

import com.shop.concurrency.common.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.context.annotation.Primary;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Item extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;
}
