package com.infominez.catalog.user.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Allow CORS from the API Gateway on port 5550
        registry.addMapping("/v3/api-docs/**")
                .allowedOrigins("http://localhost:5550")  // API Gateway URL
                .allowedMethods("GET")
                .allowCredentials(true)
                .allowedHeaders("*");
    }
}

