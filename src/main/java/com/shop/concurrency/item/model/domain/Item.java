package com.shop.concurrency.item.model.domain;

import com.shop.concurrency.common.exception.filter.constant.ExceptionCodeEnum;
import com.shop.concurrency.common.exception.filter.model.common.ExceptionObject;
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
    private Long stock;

    public void decreaseStock(int amount) {
        if(amount > this.stock) {
            throw new ExceptionObject(ExceptionCodeEnum.OverstockDeductionException);
        }
        this.stock = this.stock - amount;
    }
}
