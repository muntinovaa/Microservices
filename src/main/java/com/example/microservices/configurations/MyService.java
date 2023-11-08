package com.example.microservices.configurations;

import com.example.microservices.configurations.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyService {

    @Autowired
    private AppProperties appProperties;

    public String printAppInfo() {
        System.out.println("App Name: " + appProperties.getName());
        System.out.println("App Version: " + appProperties.getVersion());
        return appProperties.getName();
    }


}
