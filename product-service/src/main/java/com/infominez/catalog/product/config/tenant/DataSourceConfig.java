package com.infominez.catalog.product.config.tenant;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    public DataSource dataSource() {
        System.out.println("Setting up data source");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/order");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");

        TenantRoutingDataSource routingDataSource = new TenantRoutingDataSource();
        routingDataSource.setDefaultTargetDataSource(dataSource);
        routingDataSource.afterPropertiesSet();

        return routingDataSource;
    }
}
