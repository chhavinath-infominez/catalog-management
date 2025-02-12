package com.infominez.catalog.user.controller;

import com.infominez.catalog.user.base.BaseResponse;
import com.infominez.catalog.user.entity.User;
import com.infominez.catalog.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/registerUser")
    public BaseResponse<User> registerUser(@RequestBody User user) {
        log.info("Registering user : {}", user);
        return userService.registerUser(user);
    }

    @GetMapping("/getUserById")
    public BaseResponse<User> getUserById(@RequestParam("id") Long id) {
        log.info("Fetching user by id : {}", id);
        return userService.getUserById(id);
    }

    @GetMapping("/getUserByPhone")
    public BaseResponse<User> getUserByPhone(@RequestParam("phone") String phone) {
        log.info("Fetching user by phone : {}", phone);
        return userService.getUserByPhone(phone);
    }

    @PostMapping("/updateUser")
    public BaseResponse<User> updateUser(@RequestBody User user) {
        log.info("Updating user by id : {}", user);
        return userService.updateUser(user);
    }

    @DeleteMapping("/deleteUser")
    public BaseResponse<User> deleteUser(@RequestParam("id") Long id) {
        log.info("Deleting user by id : {}", id);
        return userService.deleteUser(id);
    }
}
