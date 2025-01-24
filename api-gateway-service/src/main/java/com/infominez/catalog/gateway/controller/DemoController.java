package com.infominez.catalog.gateway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/api/demo")
public class DemoController {

    @GetMapping
    public ResponseEntity<String> demo() {
        return ResponseEntity.ok("Demo endpoint is working!");
    }
}

