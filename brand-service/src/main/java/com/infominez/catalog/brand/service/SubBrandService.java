package com.infominez.catalog.brand.service;

import com.infominez.catalog.brand.base.BaseResponse;
import com.infominez.catalog.brand.entity.SubBrand;
import com.infominez.catalog.brand.repository.SubBrandRepository;
import com.infominez.catalog.brand.Utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class SubBrandService {

    private final SubBrandRepository subBrandRepository;

    // Create SubBrand
    public BaseResponse createSubBrand(SubBrand subBrand) {
        log.info("Creating SubBrand: {}", subBrand);
        BaseResponse response = new BaseResponse();
        try {
            if (subBrand == null) {
                response.setStatus(302);
                response.setMessage(StringUtils.INVALID_REQUEST);
                return response;
            }

            subBrand.setCreatedDate(new Date());
            subBrand.setUpdatedDate(new Date());

            SubBrand savedSubBrand = subBrandRepository.save(subBrand);

            if (savedSubBrand != null) {
                response.setStatus(200);
                response.setMessage(StringUtils.SUBBRAND_CREATED_SUCCESSFULLY);
                response.setResponse(savedSubBrand);
            }
        } catch (Exception e) {
            log.error("Exception while creating SubBrand: {}", subBrand, e);
            response.setInternalServerError();
        }
        return response;
    }

    // Get all SubBrands
    public BaseResponse getAllSubBrands() {
        log.info("Fetching all SubBrands");
        BaseResponse response = new BaseResponse();
        try {
            List<SubBrand> subBrands = subBrandRepository.findAll();
            if (subBrands != null && !subBrands.isEmpty()) {
                response.setStatus(200);
                response.setMessage(StringUtils.SUCCESS);
                response.setResponse(subBrands);
            } else {
                response.setStatus(302);
                response.setMessage(StringUtils.SUBBRAND_NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Exception while fetching all SubBrands", e);
            response.setInternalServerError();
        }
        return response;
    }

    // Get SubBrand by ID
    public BaseResponse getSubBrandById(Long id) {
        log.info("Fetching SubBrand by ID: {}", id);
        BaseResponse response = new BaseResponse();
        try {
            if (id == null) {
                response.setStatus(302);
                response.setMessage(StringUtils.INVALID_REQUEST);
                return response;
            }

            SubBrand subBrand = subBrandRepository.findById(id).orElse(null);
            if (subBrand != null) {
                response.setStatus(200);
                response.setMessage(StringUtils.SUCCESS);
                response.setResponse(subBrand);
            } else {
                response.setStatus(302);
                response.setMessage(StringUtils.SUBBRAND_NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Exception while fetching SubBrand by ID: {}", id, e);
            response.setInternalServerError();
        }
        return response;
    }

    // Update SubBrand
    public BaseResponse updateSubBrand(SubBrand subBrand) {
        log.info("Updating SubBrand: {}", subBrand);
        BaseResponse response = new BaseResponse();
        try {
            if (subBrand == null || subBrand.getSubBrandId() == null) {
                response.setStatus(302);
                response.setMessage(StringUtils.INVALID_REQUEST);
                return response;
            }

            SubBrand existingSubBrand = subBrandRepository.findById(subBrand.getSubBrandId()).orElse(null);

            if (existingSubBrand != null) {
                if (subBrand.getName() != null && !subBrand.getName().isEmpty()) {
                    existingSubBrand.setName(subBrand.getName());
                }
                if (subBrand.getDescription() != null && !subBrand.getDescription().isEmpty()) {
                    existingSubBrand.setDescription(subBrand.getDescription());
                }
                if (subBrand.getEmail() != null && !subBrand.getEmail().isEmpty()) {
                    existingSubBrand.setEmail(subBrand.getEmail());
                }
                if (subBrand.getMobile() != null && !subBrand.getMobile().isEmpty()) {
                    existingSubBrand.setMobile(subBrand.getMobile());
                }
                if (subBrand.getAddress() != null && !subBrand.getAddress().isEmpty()) {
                    existingSubBrand.setAddress(subBrand.getAddress());
                }
                if (subBrand.getCity() != null && !subBrand.getCity().isEmpty()) {
                    existingSubBrand.setCity(subBrand.getCity());
                }
                if (subBrand.getState() != null && !subBrand.getState().isEmpty()) {
                    existingSubBrand.setState(subBrand.getState());
                }
                if (subBrand.getCountry() != null && !subBrand.getCountry().isEmpty()) {
                    existingSubBrand.setCountry(subBrand.getCountry());
                }
                if (subBrand.getPostalCode() != null && !subBrand.getPostalCode().isEmpty()) {
                    existingSubBrand.setPostalCode(subBrand.getPostalCode());
                }
                if (subBrand.getLogoUrl() != null && !subBrand.getLogoUrl().isEmpty()) {
                    existingSubBrand.setLogoUrl(subBrand.getLogoUrl());
                }
                if (subBrand.getWebsite() != null && !subBrand.getWebsite().isEmpty()) {
                    existingSubBrand.setWebsite(subBrand.getWebsite());
                }
                if (subBrand.getStatus() != null) {
                    existingSubBrand.setStatus(subBrand.getStatus());
                }

                existingSubBrand.setUpdatedBy(1);  // assuming it's "system" or from a session context
                existingSubBrand.setUpdatedDate(new Date());

                SubBrand updatedSubBrand = subBrandRepository.save(existingSubBrand);

                response.setStatus(200);
                response.setMessage(StringUtils.SUBBRAND_UPDATED_SUCCESSFULLY);
                response.setResponse(updatedSubBrand);
            } else {
                response.setStatus(302);
                response.setMessage(StringUtils.SUBBRAND_NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Exception while updating SubBrand: {}", subBrand, e);
            response.setInternalServerError();
        }
        return response;
    }

    // Delete SubBrand
    public BaseResponse deleteSubBrand(Long id) {
        log.info("Deleting SubBrand with ID: {}", id);
        BaseResponse response = new BaseResponse();
        try {
            if (id == null) {
                response.setStatus(302);
                response.setMessage(StringUtils.INVALID_REQUEST);
                return response;
            }

            SubBrand subBrand = subBrandRepository.findById(id).orElse(null);
            if (subBrand != null) {
                subBrandRepository.delete(subBrand);
                response.setStatus(200);
                response.setMessage(StringUtils.SUBBRAND_DELETED_SUCCESSFULLY);
            } else {
                response.setStatus(302);
                response.setMessage(StringUtils.SUBBRAND_NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Exception while deleting SubBrand with ID: {}", id, e);
            response.setInternalServerError();
        }
        return response;
    }
}
