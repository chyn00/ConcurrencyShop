package com.shop.concurrency.common.exception.filter.constant;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionCodeEnum {

    DuplicatedMemberException("중복된 회원입니다. 다시 확인해주세요.", HttpStatus.CONFLICT),
    ItemOptimisticLockException("일시적인 장애로 주문에 실패하였습니다. 다시 시도해 주세요.", HttpStatus.CONFLICT);
    private String message;
    private HttpStatus httpStatus;

    ExceptionCodeEnum(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
