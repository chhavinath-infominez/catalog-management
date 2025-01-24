package com.infominez.catalog.master.controller;

import com.infominez.catalog.master.base.BaseResponse;
import com.infominez.catalog.master.entity.Tenant;
import com.infominez.catalog.master.service.TenantService;
import com.infominez.catalog.master.wrapper.request.TenantRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/tenant")
public class TenantController {

    private final TenantService tenantService;

    @PostMapping("/createTenant")
    public BaseResponse createTenant(@RequestBody TenantRequest tenantRequest) throws SQLException {
        log.info("creating tenant : {}", tenantRequest);
        return tenantService.createTenant(tenantRequest);
    }
}
