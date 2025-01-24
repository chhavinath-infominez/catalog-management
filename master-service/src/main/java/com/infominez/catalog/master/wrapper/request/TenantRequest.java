package com.infominez.catalog.master.wrapper.request;

import lombok.Data;

@Data
public class TenantRequest {

    private String name;

    private String tenantId;

    private String logo;

    private String address;
}
