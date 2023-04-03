package com.example.initialProject.account.model.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FindMembersResponse {

    @JsonIgnore
    private Long id;

    private String name;

}
