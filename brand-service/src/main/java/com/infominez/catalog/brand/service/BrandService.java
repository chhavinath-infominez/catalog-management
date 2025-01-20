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

@Service
@Slf4j
@AllArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;


    public BaseResponse createBrand(Brand brand) {
        log.info(this.getClass().getName() + " :- createBrand() brand : {}", brand);
        BaseResponse response = new BaseResponse();
        try {
            if (brand == null) {
                response.setStatus(302);
                response.setMessage(StringUtils.INVALID_REQUEST);
                return response;
            }
            brand.setCreatedBy(1);  // Adjust based on user context
            brand.setUpdatedBy(1);  // Adjust based on user context
            brand.setCreatedDate(new Date());
            brand.setUpdatedDate(new Date());
            brandRepository.save(brand);
        } catch (Exception e) {
            log.error("Exception in createBrand() brand: {}", brand, e);
            response.setInternalServerError();
        }
        return response;
    }

    public BaseResponse getAllBrands() {
        log.info(this.getClass().getName() + " :- getAllBrands()");
        BaseResponse response = new BaseResponse();
        try {
            List<Brand> brands = brandRepository.findAll();
            if (!brands.isEmpty()) {
                response.setStatus(200);
                response.setMessage(StringUtils.SUCCESS);
                response.setResponse(brands);
            } else {
                response.setStatus(302);
                response.setMessage(StringUtils.BRAND_NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Exception in getAllBrands()", e);
            response.setInternalServerError();
        }
        return response;
    }

    public BaseResponse getBrandById(Long id) {
        log.info(this.getClass().getName() + " :- getBrandById() id: {}", id);
        BaseResponse response = new BaseResponse();
        try {
            if (id == null) {
                response.setStatus(302);
                response.setMessage(StringUtils.INVALID_REQUEST);
                return response;
            }
            Optional<Brand> brand = brandRepository.findById(id);
            if (brand.isPresent()) {
                response.setStatus(200);
                response.setMessage(StringUtils.SUCCESS);
                response.setResponse(brand.get());
            } else {
                response.setStatus(302);
                response.setMessage(StringUtils.BRAND_NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Exception in getBrandById() id: {}, Exception: {}", id, e.getMessage());
            response.setInternalServerError();
        }
        return response;
    }


    public BaseResponse updateBrand(Brand brand) {
        log.info(this.getClass().getName() + " :- updateBrand() brand: {}", brand);
        BaseResponse response = new BaseResponse();
        try {
            if (brand == null) {
                response.setStatus(302);
                response.setMessage(StringUtils.INVALID_REQUEST);
                return response;
            }

            if (brand.getId() != null) {
                Brand existingBrand = brandRepository.findById(brand.getId()).orElse(null);

                if (existingBrand != null) {
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

                    existingBrand.setUpdatedBy(1);
                    existingBrand.setUpdatedDate(new Date());

                    existingBrand = brandRepository.save(existingBrand);

                    response.setStatus(200);
                    response.setMessage(StringUtils.BRAND_UPDATED_SUCCESSFULLY);
                    response.setResponse(existingBrand);
                } else {
                    response.setStatus(302);
                    response.setMessage(StringUtils.BRAND_NOT_FOUND);
                }
            } else {
                response.setStatus(302);
                response.setMessage(StringUtils.INVALID_REQUEST);
            }
        } catch (Exception e) {
            log.error("Exception in updateBrand() brand: {}", brand, e);
            response.setInternalServerError();
        }
        return response;
    }



    public BaseResponse deleteBrand(Long id) {
        log.info(this.getClass().getName() + " :- deleteBrand() id: {}", id);
        BaseResponse response = new BaseResponse();
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
