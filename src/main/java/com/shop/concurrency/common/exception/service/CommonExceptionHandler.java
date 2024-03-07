package com.shop.concurrency.common.exception.service;


import com.shop.concurrency.common.exception.filter.model.common.ExceptionObject;
import com.shop.concurrency.common.exception.filter.model.response.ExceptionJsonResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class CommonExceptionHandler {
    private static final String CONTEXT_TYPE = "application/json;charset=UTF-8";

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ExceptionObject.class)
    public void exceptionObjectExceptionHandler(HttpServletRequest request, HttpServletResponse response, ExceptionObject exceptionObject) {
        response.setContentType(CONTEXT_TYPE);
        ExceptionJsonResponse exceptionJsonResponseObject = ExceptionJsonResponse.builder()
                .url(request.getRequestURI())
                .message(exceptionObject.getMessage())
                .build();
        try {
            response.getWriter().write(exceptionJsonResponseObject.toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
