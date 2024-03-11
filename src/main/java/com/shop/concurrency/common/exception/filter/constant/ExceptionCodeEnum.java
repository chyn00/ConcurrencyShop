package com.shop.concurrency.common.exception.filter.constant;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionCodeEnum {

    DuplicatedMemberException("중복된 회원입니다. 다시 확인해주세요.", HttpStatus.CONFLICT),
    OverstockDeductionException("재고보다 많은 양을 주문하셨습니다. 다시 주문 해 주세요.", HttpStatus.BAD_REQUEST);
    private String message;
    private HttpStatus httpStatus;

    ExceptionCodeEnum(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
