package com.infominez.catalog.gateway.repository;

import com.infominez.catalog.gateway.entity.TenantDataSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantDataSourceRepository extends JpaRepository<TenantDataSource, Long> {
}
