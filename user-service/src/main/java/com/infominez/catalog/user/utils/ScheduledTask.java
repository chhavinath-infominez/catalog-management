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

    @PostConstruct
    public void runImmediately() {
        initializeTenantDataSource();  // Call the scheduled method directly on startup
    }

    @Scheduled(cron = "0 */5 * * * ?")
    public void initializeTenantDataSource() {
        log.info("Initializing tenant data source started at : {}", new Date());
        try {
            Map<String, DatabaseConfig> tempMap = new ConcurrentHashMap<>();
            List<DatabaseConfig> tenantDatabaseList = masterServiceFeign.getAllDatabaseConfig();
            System.out.println(tenantDatabaseList);
//            String responseString = response.getResponse().toString();
//            System.out.println(responseString);
//            ObjectMapper objectMapper = new ObjectMapper();
//            List<DatabaseConfig> tenantDatabaseList = objectMapper.readValue(responseString, objectMapper.getTypeFactory().constructCollectionType(List.class, DatabaseConfig.class));
            log.info("Database list size : {}", tenantDatabaseList.size() );
            tenantDatabaseList.forEach(tenantDatabase -> {
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
