package com.infominez.catalog.order.repository;

import com.infominez.catalog.order.entity.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingRepository extends JpaRepository<Shipping, Long> {
}
