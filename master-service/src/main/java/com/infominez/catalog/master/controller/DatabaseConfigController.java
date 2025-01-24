package com.infominez.catalog.master.controller;

import com.infominez.catalog.master.base.BaseResponse;
import com.infominez.catalog.master.entity.DatabaseConfig;
import com.infominez.catalog.master.service.DatabaseConfigService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/databaseConfig")
public class DatabaseConfigController {

    private final DatabaseConfigService databaseConfigService;

    @GetMapping("/getAllDatabaseConfig")
    public List<DatabaseConfig> getAllDatabaseConfig() {
        log.info("fetching all database config");
        return databaseConfigService.getAllDataSource();
    }


}
