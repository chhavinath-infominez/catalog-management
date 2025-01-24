package com.infominez.catalog.user.service;

import com.infominez.catalog.user.base.BaseResponse;
import com.infominez.catalog.user.entity.Address;
import com.infominez.catalog.user.entity.User;
import com.infominez.catalog.user.repository.AddressRepository;
import com.infominez.catalog.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    private final UserRepository userRepository;

    public BaseResponse<Address> addAddress(String username, Address address) {
        log.info("Adding user address : {}", address);
        BaseResponse<Address> response = new BaseResponse<>();
        try {
            User user = userRepository.findByPhone(username).orElse(null);
            if (user == null) {
                return response.set(302, "User not found");
            }
            address.setUser(user);
            address = addressRepository.save(address);
            response.set(200, "Success", address);
        } catch (Exception e) {
            log.error("Exception while adding user address : ", e);
            response.setSomethingWentWrong();
        }
        return response;
    }

    public BaseResponse<List<Address>> getAddressList(String username) {
        log.info("Fetching address list by user : {}", username);
        BaseResponse<List<Address>> response = new BaseResponse<>();
        try {
            User user = userRepository.findByPhone(username).orElse(null);
            if (user == null) {
                return response.set(302, "User not found");
            }
            List<Address> addressList = addressRepository.findByUser(user);
            if (addressList.isEmpty()) {
                return response.set(302, "Address not found");
            }
            response.set(200, "Success", addressList);
        } catch (Exception e) {
            log.error("Exception while fetching address list : ", e);
            response.setSomethingWentWrong();
        }
        return response;
    }

    public BaseResponse<Address> getAddress(String username, Long id) {
        log.info("Fetching address by user : {} and address id : {}", username, id);
        BaseResponse<Address> response = new BaseResponse<>();
        try {
            User user = userRepository.findByPhone(username).orElse(null);
            if (user == null) {
                return response.set(302, "User not found");
            }
            Address address = addressRepository.findByUserAndId(user, id).orElse(null);
            if (address == null) {
                return response.set(302, "Address not found");
            }
            response.set(200, "Success", address);
        } catch (Exception e) {
            log.error("Exception while fetching address : ", e);
            response.setSomethingWentWrong();
        }
        return response;
    }

    public BaseResponse<Address> updateAddress(Address address) {
        log.info("Updating address : {}", address);
        BaseResponse<Address> response = new BaseResponse<>();
        try {
            Address savedAddress = addressRepository.findById(address.getId()).orElse(null);
            if (savedAddress == null) {
                return response.set(302, "Address not found");
            }
            savedAddress = addressRepository.save(address);
            response.set(200, "Success", savedAddress);
        } catch (Exception e) {
            log.error("Exception while updating address : ", e);
            response.setSomethingWentWrong();
        }
        return response;
    }

    public BaseResponse<Address> deleteAddress(Address address) {
        log.info("Deleting address : {}", address);
        BaseResponse<Address> response = new BaseResponse<>();
        try {
            Address savedAddress = addressRepository.findById(address.getId()).orElse(null);
            if (savedAddress == null) {
                return response.set(302, "Address not found");
            }
            addressRepository.delete(savedAddress);
            response.set(200, "Success");
        } catch (Exception e) {
            log.error("Exception while deleting address : ", e);
            response.setSomethingWentWrong();
        }
        return response;
    }
}
