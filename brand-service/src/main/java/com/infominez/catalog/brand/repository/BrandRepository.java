package com.infominez.catalog.brand.repository;

import com.infominez.catalog.brand.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    // Custom queries can be added here if needed
}