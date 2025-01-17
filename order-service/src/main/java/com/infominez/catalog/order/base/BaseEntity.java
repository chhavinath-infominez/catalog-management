package com.infominez.catalog.order.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@MappedSuperclass
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

    @PrePersist
    protected void onCreate() {
        this.createdDate = new Date();
        this.updatedDate = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedDate = new Date();
    }
}
