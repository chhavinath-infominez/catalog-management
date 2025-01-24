package com.infominez.catalog.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.infominez.catalog.auth.base.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@ToString
@NoArgsConstructor
@Table(name = "user")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false, nullable = false)
    private Integer userId;

    @Column(name = "user_name")
    private String username;

    @Column(name = "phone")
    private String phone;

    @Column(name = "pin")
    private String pin;

    @Column(name = "profile_url")
    private String profileUrl;

    @Column(name = "is_active")
    private Boolean isActive;

    @JoinColumn(name = "country_id", columnDefinition = "INT UNSIGNED")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Country country;

    @Column(name = "firebase_id")
    private String fireBaseId;

    @Column(name = "last_login_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoginTime;
}
