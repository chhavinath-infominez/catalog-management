package com.infominez.catalog.auth.mapper;

import com.infominez.catalog.auth.wrapper.RegistrationForm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
@AllArgsConstructor
public class UserMapper {
//    public User toEntity(RegistrationForm registrationForm) {
//        log.info("Executing toEntity() with : {}", registrationForm);
//        User user = new User();
//        try {
//            user.setUsername(registrationForm.getUsername());
//            user.setPhone(registrationForm.getPhone());
//            user.setPin(registrationForm.getPin());
//            user.setIsActive(true);
//            user.setCountry(registrationForm.getCountry());
//            user.setFireBaseId(registrationForm.getFireBaseId());
//            user.setLastLoginTime(new Date());
//            user.setCreatedBy(1);
//            user.setCreatedDate(new Date());
//        } catch (Exception e) {
//            log.error("Exception while executing toEntity() : ", e);
//            user = null;
//        }
//        return user;
//    }
}
