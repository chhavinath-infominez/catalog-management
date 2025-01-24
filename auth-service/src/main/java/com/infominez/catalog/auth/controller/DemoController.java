package com.infominez.catalog.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")  // No /auth-service here
public class DemoController {

    @GetMapping
    public ResponseEntity<String> getDemo() {
        return ResponseEntity.ok("Demo endpoint of auth-service.");
    }
}
