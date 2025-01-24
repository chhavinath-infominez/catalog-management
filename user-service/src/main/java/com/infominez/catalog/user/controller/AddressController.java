package com.infominez.catalog.user.controller;

import com.infominez.catalog.user.base.BaseResponse;
import com.infominez.catalog.user.entity.Address;
import com.infominez.catalog.user.service.AddressService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/userAddress")
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/addAddress")
    public BaseResponse<Address> addAddress(@RequestHeader("username") String username,
                                            @RequestBody Address address) {
        log.info("Adding user address : {}", address);
        return addressService.addAddress(username, address);
    }

    @GetMapping("/getAddressList")
    public BaseResponse<List<Address>> getAddressList(@RequestHeader("username") String username) {
        log.info("Fetching address list by user : {}", username);
        return addressService.getAddressList(username);
    }

    @GetMapping("/getAddress")
    public BaseResponse<Address> getAddress(@RequestHeader("username") String username,
                                            @RequestParam("id") Long id) {
        log.info("Fetching address by user : {} and address id : {}", username, id);
        return addressService.getAddress(username, id);
    }

    @PostMapping("/updateAddress")
    public BaseResponse<Address> updateAddress(@RequestBody Address address) {
        log.info("Updating address : {}", address);
        return addressService.updateAddress(address);
    }

    @DeleteMapping("/deleteAddress")
    public BaseResponse<Address> deleteAddress(@RequestBody Address address) {
        log.info("Deleting address : {}", address);
        return addressService.deleteAddress(address);
    }
}
