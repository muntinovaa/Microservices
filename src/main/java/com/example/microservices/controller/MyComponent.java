package com.example.microservices.controller;

import com.example.microservices.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Component
public class MyComponent {
    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    public String printAppInfo() {
        System.out.println("App Name: " + appName);
        System.out.println("App Version: " + appVersion);
        return appName;
    }
}
