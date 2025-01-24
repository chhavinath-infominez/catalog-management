package com.infominez.catalog.user.repository;

import com.infominez.catalog.user.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
}
