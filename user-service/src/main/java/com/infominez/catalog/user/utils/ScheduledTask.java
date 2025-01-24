package com.infominez.catalog.user.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infominez.catalog.user.UserServiceApplication;
import com.infominez.catalog.user.base.BaseResponse;
import com.infominez.catalog.user.feign.MasterServiceFeign;
import com.infominez.catalog.user.wrapper.DatabaseConfig;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@AllArgsConstructor
public class ScheduledTask {

    private final MasterServiceFeign masterServiceFeign;

    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    public void runImmediately() {
        initializeTenantDataSource();
    }

    @Scheduled(cron = "0 */5 * * * ?")
    public void initializeTenantDataSource() {
        log.info("Initializing tenant data source started at : {}", new Date());
        try {
            Map<String, DatabaseConfig> tempMap = new ConcurrentHashMap<>();
            BaseResponse<List<DatabaseConfig>> response = masterServiceFeign.getAllDatabaseConfig();
            log.info("Database list size : {}", response.getResponse().size() );

            response.getResponse().forEach(tenantDatabase -> {
                tempMap.put(tenantDatabase.getTenantId(), tenantDatabase);
            });

            synchronized (UserServiceApplication.tenantDataBaseMap) {
                UserServiceApplication.tenantDataBaseMap = tempMap;
            }
        } catch (Exception e) {
            log.error("Exception while initializing tenant data source : ", e);
        }
        log.info("Initializing tenant data source completed at : {}", new Date());
    }
}
