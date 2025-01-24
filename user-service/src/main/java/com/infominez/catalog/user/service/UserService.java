package com.infominez.catalog.user.service;

import com.infominez.catalog.user.entity.User;
import com.infominez.catalog.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userProfileRepository;

    public User getUserProfileById(Long id) {
        return userProfileRepository.findById(id).orElse(null);
    }
}
