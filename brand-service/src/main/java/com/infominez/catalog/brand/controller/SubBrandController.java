package com.infominez.catalog.brand.controller;

import com.infominez.catalog.brand.base.BaseResponse;
import com.infominez.catalog.brand.entity.SubBrand;
import com.infominez.catalog.brand.service.SubBrandService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subBrand")
@AllArgsConstructor
@Slf4j
public class SubBrandController {

    private final SubBrandService subBrandService;

    @PostMapping("/createSubBrand")
    public BaseResponse createSubBrand(@RequestBody SubBrand subBrand) {
        log.info("Creating SubBrand: {}", subBrand);
        return subBrandService.createSubBrand(subBrand);
    }

    @GetMapping("/getAllSubBrands")
    public BaseResponse getAllSubBrands() {
        log.info("Fetching all SubBrands");
        return subBrandService.getAllSubBrands();
    }

    @GetMapping("/getSubBrandById/{id}")
    public BaseResponse getSubBrandById(@PathVariable("id") Long id) {
        log.info("Fetching SubBrand by ID: {}", id);
        return subBrandService.getSubBrandById(id);
    }

    @PostMapping("/updateSubBrand")
    public BaseResponse updateSubBrand(@RequestBody SubBrand subBrand) {
        log.info("Updating SubBrand: {}", subBrand);
        return subBrandService.updateSubBrand(subBrand);
    }

    @DeleteMapping("/deleteSubBrand/{id}")
    public BaseResponse deleteSubBrand(@PathVariable("id") Long id) {
        log.info("Deleting SubBrand with ID: {}", id);
        return subBrandService.deleteSubBrand(id);
    }
}
