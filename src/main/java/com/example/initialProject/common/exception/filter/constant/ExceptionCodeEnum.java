package com.example.initialProject.common.exception.filter.constant;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionCodeEnum {

    DuplicatedMemberException("중복된 회원입니다. 다시 확인해주세요.", HttpStatus.CONFLICT);
    private String message;
    private HttpStatus httpStatus;

    ExceptionCodeEnum(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
