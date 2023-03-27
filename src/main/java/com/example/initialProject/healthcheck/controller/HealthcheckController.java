package com.example.initialProject.healthcheck.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthcheckController {

    @GetMapping("/check")
    public String hello() {
        return "is OK";
    }
}