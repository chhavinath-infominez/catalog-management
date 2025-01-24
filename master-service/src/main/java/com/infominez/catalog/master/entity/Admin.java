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
@Table(name = "admin")
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Admin extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumUtils.Role role;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumUtils.AdminStatus status;
}
