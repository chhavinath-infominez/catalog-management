package com.infominez.catalog.brand.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @CreatedBy
    @JsonIgnore
    @Column(name = "created_by",  length = 50, updatable = false)
    private Integer createdBy;

    @CreatedDate
    @JsonIgnore
    @Column(name = "created_date", updatable = false)
    private Date createdDate;

    @JsonIgnore
    @Column(name = "updated_by", length = 50)
    private Integer updatedBy;

    @JsonIgnore
    @Column(name = "updated_date")
    private Date updatedDate;
}
