package com.infominez.catalog.master.service;

import com.infominez.catalog.master.entity.DatabaseConfig;
import com.infominez.catalog.master.repository.DatabaseConfigRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class FlywayService {

    private final DatabaseConfigRepository databaseConfigRepository;

    @PostConstruct
    public void applyMigrationsForAllTenants() {
        List<DatabaseConfig> databaseConfigList = databaseConfigRepository.findAll();

        databaseConfigList.forEach(databaseConfig -> {
            String dbUrl = databaseConfig.getUrl();
            String dbUsername = databaseConfig.getUsername();
            String dbPassword = databaseConfig.getPassword();

            Flyway flyway = Flyway.configure()
                    .dataSource(dbUrl, dbUsername, dbPassword)
                    .locations("classpath:db/tenant")  // Tenant-specific migration scripts
                    .table("flyway_schema_history")  // History table per tenant
                    .load();
            flyway.migrate();
        });

    }
}
