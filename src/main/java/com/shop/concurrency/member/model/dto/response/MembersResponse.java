package com.shop.concurrency.member.model.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shop.concurrency.order.domain.Orders;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MembersResponse {

    @JsonIgnore
    private Long id;

    private String name;

    private List<Orders> orders;
}
