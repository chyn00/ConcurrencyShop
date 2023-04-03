package com.example.initialProject.exception.filter;

import com.example.initialProject.exception.filter.model.common.ExceptionObject;
import com.example.initialProject.exception.filter.model.response.ExceptionJsonResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class ExceptionFilter extends OncePerRequestFilter {
    private static final String CONTEXT_TYPE = "application/json;charset=UTF-8";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            filterChain.doFilter(request, response);
        } catch (ExceptionObject exceptionObject) {
            setErrorResponse(exceptionObject.getHttpStatus(), response, request.getRequestURI(), exceptionObject.getMessage());
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, String url, String message) {
        response.setStatus(status.value());
        response.setContentType(CONTEXT_TYPE);
        ExceptionJsonResponse exceptionJsonResponseObject = ExceptionJsonResponse.builder()
                .url(url)
                .message(message)
                .build();
        try {
            response.getWriter().write(exceptionJsonResponseObject.toJson());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
