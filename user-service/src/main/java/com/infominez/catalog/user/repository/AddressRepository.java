package com.infominez.catalog.user.repository;

import com.infominez.catalog.user.entity.Address;
import com.infominez.catalog.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByUser(User user);

   Optional<Address> findByUserAndId(User user, Long id);
}
