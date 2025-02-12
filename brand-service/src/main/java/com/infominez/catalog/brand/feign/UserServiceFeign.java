package com.infominez.catalog.brand.feign;

import com.infominez.catalog.brand.base.BaseResponse;
import com.infominez.catalog.brand.wrapper.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", url = "http://localhost:5554", path = "/user-service",
        configuration = FeignEncoderConfig.class)
public interface UserServiceFeign {

    @RequestMapping(value = "/user/getUserByPhone", method = RequestMethod.GET,
            consumes = "application/json", produces = "application/json")
    BaseResponse<User> getUserByPhone(@RequestParam("phone") String phone);
}
