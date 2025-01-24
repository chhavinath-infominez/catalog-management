package com.infominez.catalog.master.service;

import com.infominez.catalog.master.base.BaseResponse;
import com.infominez.catalog.master.entity.DatabaseConfig;
import com.infominez.catalog.master.entity.Tenant;
import com.infominez.catalog.master.repository.DatabaseConfigRepository;
import com.infominez.catalog.master.repository.TenantRepository;
import com.infominez.catalog.master.utils.EnumUtils;
import com.infominez.catalog.master.wrapper.request.TenantRequest;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.flywaydb.core.Flyway;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
@Service
@AllArgsConstructor
public class TenantService {

    @Autowired
    private DataSource masterDataSource;

    private final TenantRepository tenantRepository;

    private final DatabaseConfigRepository databaseConfigRepository;


    public BaseResponse createTenant(TenantRequest tenantRequest) {
        log.info("creating tenant : {}", tenantRequest);
        HikariDataSource hikariDataSource;
        if (masterDataSource instanceof HikariDataSource) {
            hikariDataSource = (HikariDataSource) masterDataSource;
        } else {
            throw new IllegalStateException("DataSource is not an instance of HikariDataSource");
        }
        BaseResponse response = new BaseResponse();
        Tenant tenant = new Tenant();
        tenant.setTenantId(tenantRequest.getTenantId());
        tenant.setName(tenantRequest.getName());
        tenant.setLogo(tenantRequest.getLogo());
        tenant.setAddress(tenantRequest.getAddress());
        tenant.setStatus(EnumUtils.TenantStatus.ACTIVE);
        tenant.setCreatedBy(1);

        tenant = tenantRepository.save(tenant);
        createDatabase(tenant);

        DatabaseConfig databaseConfig = new DatabaseConfig();
        databaseConfig.setTenantId(tenant.getTenantId());
        databaseConfig.setUrl(hikariDataSource.getJdbcUrl().replace("master", tenant.getTenantId()));
        databaseConfig.setUsername(hikariDataSource.getUsername());
        databaseConfig.setPassword(hikariDataSource.getPassword());
        databaseConfigRepository.save(databaseConfig);
        return response.set(200, "SUCCESS", tenant);
    }

    private void createDatabase(Tenant tenant) {
        try (Connection connection = masterDataSource.getConnection();
             Statement statement = connection.createStatement()) {

             String createDbQuery = "CREATE DATABASE " + tenant.getTenantId();
             statement.executeUpdate(createDbQuery);
             System.out.println("Database " + tenant.getTenantId() + " created successfully.");

             Flyway flyway = Flyway.configure()
                    .dataSource("jdbc:postgresql://localhost:5432/" + tenant.getTenantId(),
                            "postgres", "postgres")
                    .locations("classpath:db/tenant")
                    .load();
             flyway.migrate();

        } catch (Exception e) {
            log.error("Failed to create tenant database : ", e);
        }
    }
}
