package com.infominez.catalog.auth.wrapper;

import lombok.Data;

@Data
public class DatabaseConfig {

    private Long id;

    private String tenantId;

    private String url;

    private String username;

    private String password;
}
