package com.example.initialProject.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.stream.Collectors;

public class ObjectConvertUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
    * list to List Dto 변환
    * */
    public static <T> List<T> listToListDto(List<?> voList, Class<T> dtoClass){

        return  voList.stream()
                .map(vo -> objectMapper.convertValue(vo, dtoClass))
                .collect(Collectors.toList());
    }

    /**
     * to Dto 변환
     * */
    public static <T> T toDto(Object vo, Class<T> dtoClass){
        return  objectMapper.convertValue(vo, dtoClass);
    }

}
