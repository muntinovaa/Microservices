package com.example.microservices.configurations;

import com.example.microservices.service.MyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ProfileConfig {
/*
    @Bean
    @Profile("dev")
    public MyService devMyService() {
        return new DevMyService();
    }

    @Bean
    @Profile("prod")
    public MyService prodMyService() {
        return new ProdMyService();
    }*/
}
