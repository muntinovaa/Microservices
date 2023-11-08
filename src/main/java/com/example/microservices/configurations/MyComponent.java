package com.example.microservices.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
