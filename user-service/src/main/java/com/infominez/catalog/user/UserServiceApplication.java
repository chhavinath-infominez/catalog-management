package com.infominez.catalog.user;

import com.infominez.catalog.user.tenant.TenantFilter;
import com.infominez.catalog.user.wrapper.DatabaseConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class UserServiceApplication {

    public static Map<String, DatabaseConfig> tenantDataBaseMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean<TenantFilter> tenantFilter() {
        FilterRegistrationBean<TenantFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TenantFilter());
        registrationBean.addUrlPatterns("/*"); // Adjust as needed
        registrationBean.setOrder(1); // Ensure it executes early
        return registrationBean;
    }

}
