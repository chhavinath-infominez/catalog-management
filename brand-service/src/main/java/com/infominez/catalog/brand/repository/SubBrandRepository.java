package com.infominez.catalog.brand.repository;

import com.infominez.catalog.brand.entity.SubBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubBrandRepository extends JpaRepository<SubBrand, Long> {
    // Custom queries can be added here if needed
}
