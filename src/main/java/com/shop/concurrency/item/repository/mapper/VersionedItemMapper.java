package com.shop.concurrency.item.repository.mapper;

import com.shop.concurrency.item.model.dto.VersionedItemRequest;
import com.shop.concurrency.item.model.dto.VersionedItemResponse;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VersionedItemMapper {

    VersionedItemResponse getItemByIdAndVersion(Long itemId);
    int updateItem(VersionedItemRequest request);
}
