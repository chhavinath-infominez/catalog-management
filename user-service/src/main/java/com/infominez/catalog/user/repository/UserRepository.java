package com.infominez.catalog.user.repository;

import com.infominez.catalog.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByPhone(String phone);
}
