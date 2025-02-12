package com.infominez.catalog.brand.service;

import com.infominez.catalog.brand.Utils.StringUtils;
import com.infominez.catalog.brand.base.BaseResponse;
import com.infominez.catalog.brand.entity.Brand;
import com.infominez.catalog.brand.repository.BrandRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    public BaseResponse<Brand> createBrand(Brand brand) {
        log.info("Creating brand : {}", brand);
        BaseResponse<Brand> response = new BaseResponse<>();
        try {
            brand = brandRepository.save(brand);
            response.set(200, "Success", brand);
        } catch (Exception e) {
            log.error("Exception while creating brand : ", e);
            response.setInternalServerError();
        }
        return response;
    }

    public BaseResponse<List<Brand>> getAllBrands() {
        log.info("Fetching all brands");
        BaseResponse<List<Brand>> response = new BaseResponse<>();
        try {
            List<Brand> brandList = brandRepository.findAll();
            if (brandList.isEmpty()) {
                return response.set(302, "Brand not found");
            }
            response.setStatus(302);
            response.setMessage(StringUtils.BRAND_NOT_FOUND);
        } catch (Exception e) {
            log.error("Exception in getAllBrands()", e);
            response.setInternalServerError();
        }
        return response;
    }

    public BaseResponse<Brand> getBrandById(Long id) {
        log.info("Fetching brand by id: {}", id);
        BaseResponse<Brand> response = new BaseResponse<>();
        try {
            if (id == null) {
                response.setStatus(302);
                response.setMessage(StringUtils.INVALID_REQUEST);
                return response;
            }
            Brand brand = brandRepository.findById(id).orElse(null);
            if (brand == null) {
                response.setStatus(302);
                response.setMessage(StringUtils.BRAND_NOT_FOUND);
            }
            response.setStatus(200);
            response.setMessage(StringUtils.SUCCESS);
            response.setResponse(brand);
        } catch (Exception e) {
            log.error("Exception while fetching brand by id: {}, Exception: {}", id, e.getMessage());
            response.setInternalServerError();
        }
        return response;
    }


    public BaseResponse<Brand> updateBrand(Brand brand) {
        log.info("Updating brand: {}", brand);
        BaseResponse<Brand> response = new BaseResponse<>();
        try {
            if (brand == null || brand.getId() == null) {
                return response.set(302, StringUtils.INVALID_REQUEST);
            }
            Brand existingBrand = brandRepository.findById(brand.getId()).orElse(null);
            if (existingBrand == null) {
                return response.set(302, StringUtils.BRAND_NOT_FOUND);
            }
            if (brand.getName() != null && !brand.getName().isEmpty()) {
                existingBrand.setName(brand.getName());
            }
            if (brand.getDescription() != null && !brand.getDescription().isEmpty()) {
                existingBrand.setDescription(brand.getDescription());
            }
            if (brand.getEmail() != null && !brand.getEmail().isEmpty()) {
                existingBrand.setEmail(brand.getEmail());
            }
            if (brand.getMobile() != null && !brand.getMobile().isEmpty()) {
                existingBrand.setMobile(brand.getMobile());
            }
            if (brand.getAddress() != null && !brand.getAddress().isEmpty()) {
                existingBrand.setAddress(brand.getAddress());
            }
            if (brand.getCity() != null && !brand.getCity().isEmpty()) {
                existingBrand.setCity(brand.getCity());
            }
            if (brand.getState() != null && !brand.getState().isEmpty()) {
                existingBrand.setState(brand.getState());
            }
            if (brand.getCountry() != null && !brand.getCountry().isEmpty()) {
                existingBrand.setCountry(brand.getCountry());
            }
            if (brand.getPostalCode() != null && !brand.getPostalCode().isEmpty()) {
                existingBrand.setPostalCode(brand.getPostalCode());
            }
            if (brand.getLogoUrl() != null && !brand.getLogoUrl().isEmpty()) {
                existingBrand.setLogoUrl(brand.getLogoUrl());
            }
            if (brand.getWebsite() != null && !brand.getWebsite().isEmpty()) {
                existingBrand.setWebsite(brand.getWebsite());
            }
            if (brand.getStatus() != null) {
                existingBrand.setStatus(brand.getStatus());
            }

            existingBrand = brandRepository.save(existingBrand);

            response.set(200, StringUtils.BRAND_UPDATED_SUCCESSFULLY, existingBrand);
        } catch (Exception e) {
            log.error("Exception while updating brand: {}", brand, e);
            response.setInternalServerError();
        }
        return response;
    }



    public BaseResponse<Brand> deleteBrand(Long id) {
        log.info(this.getClass().getName() + " :- deleteBrand() id: {}", id);
        BaseResponse<Brand> response = new BaseResponse<>();
        try {
            if (id == null) {
                response.setStatus(302);
                response.setMessage(StringUtils.INVALID_REQUEST);
                return response;
            }
            Optional<Brand> brand = brandRepository.findById(id);
            if (brand.isPresent()) {
                brandRepository.delete(brand.get());
                response.setStatus(200);
                response.setMessage(StringUtils.BRAND_DELETED_SUCCESSFULLY);
            } else {
                response.setStatus(302);
                response.setMessage(StringUtils.BRAND_NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Exception in deleteBrand() id: {}", id, e);
            response.setInternalServerError();
        }
        return response;
    }
}
