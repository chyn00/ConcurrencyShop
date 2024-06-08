package com.shop.concurrency.item.repository.mapper;

import com.shop.concurrency.item.model.dto.ItemResponse;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ItemMapper {

    ItemResponse getItemById(Long itemId);
    int updateItem(ItemResponse response);
}
