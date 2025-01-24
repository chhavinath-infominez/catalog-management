package com.infominez.catalog.auth;

import com.infominez.catalog.auth.wrapper.RegistrationForm;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.HashMap;
import java.util.Map;

@EnableDiscoveryClient
@SpringBootApplication
public class AuthServiceApplication {

    public static Map<String, RegistrationForm> registrationMap = new HashMap<>();

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }
}