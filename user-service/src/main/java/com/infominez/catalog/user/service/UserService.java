package com.infominez.catalog.user.service;

import com.infominez.catalog.user.base.BaseResponse;
import com.infominez.catalog.user.entity.User;
import com.infominez.catalog.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public BaseResponse<User> registerUser(User user) {
        log.info("Registering new user : {}", user);
        BaseResponse<User> response = new BaseResponse<>();
        try {
            user = userRepository.save(user);
            response.set(200, "Success", user);
        } catch (Exception e) {
            log.error("Exception while registering user : ", e);
            response.setSomethingWentWrong();
        }
        return response;
    }

    public BaseResponse<User> getUserById(Long userId) {
        log.info("Fetching user by id : {}", userId);
        BaseResponse<User> response = new BaseResponse<>();
        try {
            User user = userRepository.findById(userId).orElse(null);
            if (user == null) {
                return response.set(302, "User not found");
            }
            response.set(200, "Success", user);
        } catch (Exception e) {
            log.error("Exception while fetching user : ", e);
            response.setSomethingWentWrong();
        }
        return response;
    }

    public BaseResponse<User> getUserByPhone(String phone) {
        log.info("Fetching user by phone : {}", phone);
        BaseResponse<User> response = new BaseResponse<>();
        try {
            User user = userRepository.findByPhone(phone).orElse(null);
            if (user == null) {
                return response.set(302, "User not found");
            }
            response.set(200, "Success", user);
        } catch (Exception e) {
            log.error("Exception while fetching user : ", e);
            response.setSomethingWentWrong();
        }
        return response;
    }

    public BaseResponse<User> updateUser(User user) {
        log.info("Updating user : {}", user);
        BaseResponse<User> response = new BaseResponse<>();
        try {
            user = userRepository.save(user);
            response.set(200, "Success", user);
        } catch (Exception e) {
            log.error("Exception while updating user : ", e);
            response.setSomethingWentWrong();
        }
        return response;
    }

    public BaseResponse<User> deleteUser(Long userId) {
        log.info("Deleting user by id : {}", userId);
        BaseResponse<User> response = new BaseResponse<>();
        try {
            User user = userRepository.findById(userId).orElse(null);
            if (user == null) {
                return response.set(302, "User not found");
            }
            userRepository.delete(user);
            response.set(200, "Success");
        } catch (Exception e) {
            log.error("Exception while deleting user : ", e);
            response.setSomethingWentWrong();
        }
        return response;
    }

}
