package com.infominez.catalog.auth;

import com.infominez.catalog.auth.tenant.TenantFilter;
import com.infominez.catalog.auth.wrapper.DatabaseConfig;
import com.infominez.catalog.auth.wrapper.RegistrationForm;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class AuthServiceApplication {

    public static Map<String, RegistrationForm> registrationMap = new HashMap<>();

    public static Map<String, DatabaseConfig> tenantDataBaseMap = new ConcurrentHashMap<>();

    @Bean
    public FilterRegistrationBean<TenantFilter> tenantFilter() {
        FilterRegistrationBean<TenantFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TenantFilter());
        registrationBean.addUrlPatterns("/*"); // Adjust as needed
        registrationBean.setOrder(1); // Ensure it executes early
        return registrationBean;
    }

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }
}