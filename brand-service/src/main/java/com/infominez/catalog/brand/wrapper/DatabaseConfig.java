package com.infominez.catalog.brand.wrapper;

import lombok.Data;

@Data
public class DatabaseConfig {

    private Long id;

    private String tenantId;

    private String url;

    private String username;

    private String password;
}
