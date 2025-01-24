package com.infominez.catalog.master.service;

import com.infominez.catalog.master.repository.AdminRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class AdminService {

    private final AdminRepository adminUserRepository;

}
