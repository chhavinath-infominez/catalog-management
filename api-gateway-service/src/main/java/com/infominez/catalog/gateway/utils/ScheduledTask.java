package com.infominez.catalog.gateway.utils;

import com.infominez.catalog.gateway.GatewayServiceApplication;
import com.infominez.catalog.gateway.entity.TenantDataSource;
import com.infominez.catalog.gateway.repository.TenantDataSourceRepository;
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

    private final TenantDataSourceRepository tenantDataSourceRepository;

    @PostConstruct
    public void runImmediately() {
        initializeTenantDataSource();  // Call the scheduled method directly on startup
    }

    @Scheduled(cron = "0 */5 * * * ?")
    public void initializeTenantDataSource() {
        log.info("Initializing tenant data source started at : {}", new Date());
        try {
            Map<String, TenantDataSource> tempMap = new ConcurrentHashMap<>();
            List<TenantDataSource> tenantDataSourceList = tenantDataSourceRepository.findAll();
            tenantDataSourceList.forEach(tenantDataSource -> {
                tempMap.put(tenantDataSource.getTenantId(), tenantDataSource);
            });

            synchronized (GatewayServiceApplication.tenantDataSourceMap) {
                GatewayServiceApplication.tenantDataSourceMap = tempMap;
            }
        } catch (Exception e) {
            log.error("Exception while initializing tenant data source : ", e);
        }
        log.info("Initializing tenant data source completed at : {}", new Date());
    }
}
