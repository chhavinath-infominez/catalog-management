package com.infominez.catalog.auth.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import jakarta.persistence.*;
import java.util.Date;

@MappedSuperclass
@ToString
@Data
public class BaseEntity {
    @JsonIgnore
    @Column(name = "created_by")
    private Integer createdBy;

    @JsonIgnore
    @Column(name = "updated_by")
    private Integer updatedBy;

    @JsonIgnore
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @JsonIgnore
    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;


}
