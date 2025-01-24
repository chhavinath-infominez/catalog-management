package com.infominez.catalog.auth.controller;

import com.infominez.catalog.auth.base.BaseResponse;
import com.infominez.catalog.auth.entity.User;
import com.infominez.catalog.auth.wrapper.LoginForm;
import com.infominez.catalog.auth.service.AuthService;
import com.infominez.catalog.auth.service.JwtTokenService;
import com.infominez.catalog.auth.wrapper.RegistrationForm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    private final JwtTokenService jwtTokenService;

    @PostMapping("/login")
    public BaseResponse login(@RequestBody LoginForm loginForm) {
        log.info("Executing login() with : {}", loginForm);
        return authService.login(loginForm);
    }

    @PostMapping("/sendOTP")
    public BaseResponse sendOTP(@RequestBody RegistrationForm registrationForm) {
        log.info("Executing sendOTP() with : {}", registrationForm);
        return authService.sendOTP(registrationForm);
    }

    @PostMapping("/verifyOTP")
    public BaseResponse verifyOTP(@RequestBody RegistrationForm registrationForm) {
        log.info("Executing verifyOTP() with : {}", registrationForm);
        return authService.verifyOTP(registrationForm);
    }

    @PostMapping("/resetPin")
    public BaseResponse resetPin(@RequestBody RegistrationForm registrationForm, @RequestHeader("username") String username) {
        System.out.println("Username : " + username);
        log.info("Executing resetPin() with : {}", registrationForm);
        return authService.resetPin(registrationForm);
    }

    @PostMapping("/forgetPin")
    public BaseResponse forgetAndResetPin(@RequestBody RegistrationForm registrationForm) {
        log.info("Executing forgetAndResetPin() with : {}", registrationForm);
        return authService.forgetPin(registrationForm);
    }

    @PostMapping("/logout")
    public BaseResponse logout(@RequestHeader("Authorization") String tokenHeader) {
        log.info("Executing logout() : {}", tokenHeader);
        tokenHeader = tokenHeader.replaceAll("Bearer ", "");
        return jwtTokenService.invalidateToken(tokenHeader);
    }

    @GetMapping("/getUserInContext")
    public User getUserInContext(@RequestParam("token") String token) {
        log.info("Executing getUserInContext()");
        return authService.getUserInContext();
    }

    @PostMapping("/registerUser")
    public BaseResponse registerUser(@RequestBody RegistrationForm registrationForm) {
        log.info("Executing registerUser() with : {}", registrationForm);
        return authService.registerUser(registrationForm);
    }

    @GetMapping("/isTokenBlacklisted")
    public ResponseEntity<Boolean> isTokenBlacklisted(@RequestParam String token) {
        boolean isBlacklisted = jwtTokenService.isTokenBlacklisted(token);
        return ResponseEntity.ok(isBlacklisted);
    }
}
