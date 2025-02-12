package com.infominez.catalog.brand;

import com.infominez.catalog.brand.tenant.TenantFilter;
import com.infominez.catalog.brand.wrapper.DatabaseConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@EnableScheduling
@EnableFeignClients
@SpringBootApplication
public class BrandServiceApplication {

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
        SpringApplication.run(BrandServiceApplication.class, args);
    }

}
