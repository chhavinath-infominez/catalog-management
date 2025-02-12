package com.infominez.catalog.brand.controller;

import com.infominez.catalog.brand.base.BaseResponse;
import com.infominez.catalog.brand.entity.Brand;
import com.infominez.catalog.brand.service.BrandService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("brand")
public class BrandController {

    private final BrandService brandService;

    @PostMapping("/createBrand")
    public BaseResponse<Brand> createBrand(@RequestBody Brand brand) {
        log.info("createBrand() brand : {}", brand);
        return brandService.createBrand(brand);
    }

    @GetMapping("/getAllBrands")
    public BaseResponse<List<Brand>> getAllBrands() {
        log.info("Fetching all brands");
        return brandService.getAllBrands();
    }

    @GetMapping("/getBrandById/{id}")
    public BaseResponse<Brand> getBrandById(@PathVariable("id") Long id) {
        log.info("getBrandById() id : {}", id);
        return brandService.getBrandById(id);
    }

    @PostMapping("/updateBrand")
    public BaseResponse<Brand> updateBrand(@RequestBody Brand brand) {
        log.info("updateBrand() brand : {}", brand);
        return brandService.updateBrand(brand);
    }

    @DeleteMapping("/deleteBrand/{id}")
    public BaseResponse<Brand> deleteBrand(@PathVariable("id") Long id) {
        log.info("deleteBrand() id : {}", id);
        return brandService.deleteBrand(id);
    }
}

