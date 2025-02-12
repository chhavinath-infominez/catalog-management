package com.infominez.catalog.brand.feign;

import com.infominez.catalog.brand.base.BaseResponse;
import com.infominez.catalog.brand.wrapper.DatabaseConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "master-service", url = "http://localhost:5552", path = "/master-service",
            configuration = FeignEncoderConfig.class)
public interface MasterServiceFeign {

    @RequestMapping(value = "/databaseConfig/getAllDatabaseConfig", method = RequestMethod.GET,
            consumes = "application/json", produces = "application/json")
    BaseResponse<List<DatabaseConfig>> getAllDatabaseConfig();
}
