package com.infominez.catalog.product.controller;

import com.infominez.catalog.product.entity.Category;
import com.infominez.catalog.product.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    private DataSource dataSource;

    @PostMapping("/createCategory")
    public Category createCategory(@RequestBody Category category) {
        log.info("creating category : {}", category);
        TenantContext.getTenant();
        return categoryService.createCategory(category);
    }

    @GetMapping("/findById")
    public ResponseEntity findById(@RequestParam("categoryId") Long categoryId) {
        log.info("finding category by id : {}", categoryId);

        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = ((Connection) connection).getMetaData();
            System.out.println("Database URL: " + metaData.getURL());
            System.out.println("Database User: " + metaData.getUserName());
            System.out.println("Database Product Name: " + metaData.getDatabaseProductName());
            System.out.println("Database Product Version: " + metaData.getDatabaseProductVersion());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(categoryService.findById(categoryId));
    }
}
