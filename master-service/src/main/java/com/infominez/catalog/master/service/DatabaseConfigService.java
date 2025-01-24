package com.infominez.catalog.master.service;

import com.infominez.catalog.master.base.BaseResponse;
import com.infominez.catalog.master.entity.DatabaseConfig;
import com.infominez.catalog.master.repository.DatabaseConfigRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class DatabaseConfigService {

    private final DatabaseConfigRepository dataSourceRepository;

    public List<DatabaseConfig> getAllDataSource() {
        log.info("fetching all data sources");
        BaseResponse response = new BaseResponse();
//        try {
            List<DatabaseConfig> databaseConfigList = dataSourceRepository.findAll();
            if (databaseConfigList.isEmpty()) {
//                return response.set(302, "Database Config not found");
            }
//            response.set(200, "Success", databaseConfigList);
//        } catch (Exception e) {
//            log.error("Exception while fetching database config list : ", e);
//            response.setSomethingWentWrong();
//        }
//        return response;
        return databaseConfigList;
    }
}
