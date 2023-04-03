package com.example.initialProject.exception.filter.model.common;

import com.example.initialProject.exception.filter.constant.ExceptionCodeEnum;
import lombok.Getter;
import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;

@Getter
public class ExceptionObject extends NestedRuntimeException {
    private static final long serialVersionUID = 1L;
    private final HttpStatus httpStatus;
    private final String message;

    public ExceptionObject(ExceptionCodeEnum code) {
        super(code.getMessage());
        this.message = code.getMessage();
        this.httpStatus = code.getHttpStatus();
    }
}
