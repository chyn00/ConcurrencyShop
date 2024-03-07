package com.example.initialProject.utils.init;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class InitialDataUtil {

    @PostConstruct
    public void createData(){
        System.out.println("Member Data가 생성되었습니다.");
        System.out.println("Order Data가 생성되었습니다.");
        System.out.println("Item Data가 생성되었습니다.");
    }
}
