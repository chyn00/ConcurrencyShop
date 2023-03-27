package com.example.initialProject.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberDto {
    private String name;

    //todo : json ignore를 하지않고, 아예 dto는 출력을 안한다.
    //물론, entity에서는 jsonignore를 붙이던가 한다.
}