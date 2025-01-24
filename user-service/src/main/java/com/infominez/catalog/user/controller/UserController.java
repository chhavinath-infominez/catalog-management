package com.infominez.catalog.user.controller;

import com.infominez.catalog.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userProfileService;

    @GetMapping("/findById")
    public ResponseEntity findUserProfileByPhone(@RequestParam("id") Long id) {
        return ResponseEntity.ok(userProfileService.getUserProfileById(id));
    }
}
