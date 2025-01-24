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
import java.sql.Statement;

@Slf4j
@Service
@AllArgsConstructor
public class TenantService {

    @Autowired
    private DataSource masterDataSource;

    private final TenantRepository tenantRepository;

    private final DatabaseConfigRepository databaseConfigRepository;


    public BaseResponse<Tenant> createTenant(TenantRequest tenantRequest) {
        log.info("creating tenant : {}", tenantRequest);
        BaseResponse<Tenant> response = new BaseResponse<>();
        try {
            Tenant tenant = tenantRepository.findByTenantId(tenantRequest.getTenantId()).orElse(null);
            if (tenant != null) {
                return response.set(302, "Tenant already exist");
            }
            tenant = new Tenant();
            tenant.setTenantId(tenantRequest.getTenantId());
            tenant.setName(tenantRequest.getName());
            tenant.setLogo(tenantRequest.getLogo());
            tenant.setAddress(tenantRequest.getAddress());
            tenant.setStatus(EnumUtils.TenantStatus.ACTIVE);
            tenant.setCreatedBy(1);
            tenant = tenantRepository.save(tenant);

            createTenantDatabase(tenant);
            createDatabaseConfig(tenant);
            response.set(200, "Success", tenant);
        } catch (Exception e) {
            log.info("Exception while creating tenant : ", e);
            response.setSomethingWentWrong();
        }
        return response;
    }

    private void createTenantDatabase(Tenant tenant) {
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
            throw new RuntimeException(new Exception("Exception while creating tenant database : " + e));
        }
    }

    private void createDatabaseConfig(Tenant tenant) {
        try {
            HikariDataSource hikariDataSource;
            if (masterDataSource instanceof HikariDataSource) {
                hikariDataSource = (HikariDataSource) masterDataSource;
                DatabaseConfig databaseConfig = new DatabaseConfig();
                databaseConfig.setTenantId(tenant.getTenantId());
                databaseConfig.setUrl(hikariDataSource.getJdbcUrl().replace("master", tenant.getTenantId()));
                databaseConfig.setUsername(hikariDataSource.getUsername());
                databaseConfig.setPassword(hikariDataSource.getPassword());
                databaseConfigRepository.save(databaseConfig);
            } else {
                throw new IllegalStateException("DataSource is not an instance of HikariDataSource");
            }
        } catch (Exception e) {
            throw new RuntimeException(new Exception("Exception while creating database config : " + e));
        }
    }
}
