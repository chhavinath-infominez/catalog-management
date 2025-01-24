package com.infominez.catalog.master.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.infominez.catalog.master.base.BaseEntity;
import com.infominez.catalog.master.utils.EnumUtils;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tenant")
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Tenant extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "tenant_id", unique = true, nullable = false)
    private String tenantId;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumUtils.TenantStatus status;

    @Column(name = "logo")
    private String logo;

    @Column(name = "address")
    private String address;
}
