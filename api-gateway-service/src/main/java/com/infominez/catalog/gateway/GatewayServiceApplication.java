package com.infominez.catalog.gateway;

import com.infominez.catalog.gateway.entity.TenantDataSource;
import com.infominez.catalog.gateway.repository.TenantDataSourceRepository;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@EnableScheduling
@EnableDiscoveryClient
@SpringBootApplication
public class GatewayServiceApplication {

    public static Map<String, TenantDataSource> tenantDataSourceMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }

}
