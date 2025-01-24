package com.infominez.catalog.auth.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.infominez.catalog.auth.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@Entity
@ToString
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "country")
public class Country extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id", updatable = false, nullable = false)
    private Integer countryId;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "phone_code")
    private String phoneCode;

    @Column(name = "country_flag_url")
    private String countryFlagURL;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

}
