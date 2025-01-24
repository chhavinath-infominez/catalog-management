package com.infominez.catalog.auth.repository;

import com.infominez.catalog.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByPhoneAndIsActive(String phoneNumber, boolean isActive);

    User findByUserId(Integer id);

//	User findByUserName(String username);

    @Modifying
    @Transactional
    @Query(value =  "call deleteUser(:phoneNo)", nativeQuery = true)
    void deleteUser(String phoneNo);

    List<User> findAllByIsActive(boolean isActive);
}
