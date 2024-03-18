package com.shop.concurrency.member.model.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shop.concurrency.order.domain.Orders;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FindMembersResponse {

    @JsonIgnore
    private Long id;

    private String name;

    private List<Orders> orders;
}
