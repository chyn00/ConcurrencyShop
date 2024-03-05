package com.example.initialProject.common.exception.filter.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ExceptionJsonResponse {
    @JsonIgnore
    private final ObjectMapper objectMapper = new ObjectMapper();

    private String url;
    private String message;


    public String toJson() {
        try {
            return this.objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException jsonProcessingException) {
            jsonProcessingException.printStackTrace();
            return jsonProcessingException.getMessage();
        }
    }
}
