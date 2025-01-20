package com.infominez.catalog.brand.controller;

import com.infominez.catalog.brand.base.BaseResponse;
import com.infominez.catalog.brand.entity.Brand;
import com.infominez.catalog.brand.service.BrandService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("brand")
@AllArgsConstructor
@Slf4j
public class BrandController {

    private final BrandService brandService;

    @PostMapping("/createBrand")
    public BaseResponse createBrand(@RequestBody Brand brand) {
        log.info("createBrand() brand : {}", brand.toString());
        return brandService.createBrand(brand);
    }

    @GetMapping("/getAllBrands")
    public BaseResponse getAllBrands() {
        log.info("getAllBrands()");
        return brandService.getAllBrands();
    }

    @GetMapping("/getBrandById/{id}")
    public BaseResponse getBrandById(@PathVariable("id") Long id) {
        log.info("getBrandById() id : {}", id);
        return brandService.getBrandById(id);
    }

    @PostMapping("/updateBrand")
    public BaseResponse updateBrand(@RequestBody Brand brand) {
        log.info("updateBrand() brand : {}", brand);
        return brandService.updateBrand(brand);
    }

    @DeleteMapping("/deleteBrand/{id}")
    public BaseResponse deleteBrand(@PathVariable("id") Long id) {
        log.info("deleteBrand() id : {}", id);
        return brandService.deleteBrand(id);
    }
}

