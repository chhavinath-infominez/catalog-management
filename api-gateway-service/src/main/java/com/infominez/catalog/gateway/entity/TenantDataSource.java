package com.infominez.catalog.gateway.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "database_config")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TenantDataSource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "tenant_id", unique = true, nullable = false)
    private String tenantId;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;
}
