package com.infominez.catalog.auth.tenant;

import com.infominez.catalog.auth.AuthServiceApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableScheduling
public class DataSourceConfig {

    private volatile TenantRoutingDataSource tenantRoutingDataSource;

    private volatile boolean isInitialized = false;

    @Bean
    public DataSource dataSource() {
        if (tenantRoutingDataSource == null) {
            synchronized (this) {
                if (tenantRoutingDataSource == null) {
                    tenantRoutingDataSource = createRoutingDataSource();
                }
            }
        }
        return tenantRoutingDataSource;
    }

    private TenantRoutingDataSource createRoutingDataSource() {
        TenantRoutingDataSource routingDataSource = new TenantRoutingDataSource();
        Map<Object, Object> dataSources = buildDataSourceMap();
        routingDataSource.setTargetDataSources(dataSources);
        routingDataSource.setDefaultTargetDataSource(defaultDataSource()); // Default DataSource
        routingDataSource.afterPropertiesSet();
        isInitialized = true; // Mark as initialized
        return routingDataSource;
    }

    private Map<Object, Object> buildDataSourceMap() {
        Map<Object, Object> dataSources = new HashMap<>();
        System.out.println("Map size : " + AuthServiceApplication.tenantDataBaseMap.size());
        AuthServiceApplication.tenantDataBaseMap.forEach((tenantId, dbConfig) -> {
            DataSource dataSource = DataSourceBuilder.create()
                    .url(dbConfig.getUrl())
                    .username(dbConfig.getUsername())
                    .password(dbConfig.getPassword())
                    .driverClassName("org.postgresql.Driver")
                    .build();
            dataSources.put(tenantId, dataSource);
        });
        return dataSources;
    }

    private DataSource defaultDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:postgresql://localhost:5432/master")
                .username("postgres")
                .password("postgres")
                .driverClassName("org.postgresql.Driver")
                .build();
    }

    @Scheduled(fixedRate = 20000) // Refresh every 2 minutes
    public void refreshDataSource() {
        if (!isInitialized) {
            System.out.println("Data source not initialized yet, skipping refresh.");
            return;
        }
        System.out.println("Refreshing data sources...");
        synchronized (this) {
            if (tenantRoutingDataSource != null) {
                tenantRoutingDataSource.setTargetDataSources(buildDataSourceMap());
                tenantRoutingDataSource.afterPropertiesSet();
            }
        }
        System.out.println("Data sources refreshed successfully.");
    }

}
