package com.infominez.catalog.product.service;

import com.infominez.catalog.product.entity.Category;
import com.infominez.catalog.product.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category createCategory(Category category) {
        log.info("creating category : {}", category);
        try {
            category = categoryRepository.save(category);
        } catch (Exception e) {
            log.error("Exception while creating category : ", e);
            category = null;
        }
        return category;
    }

    public Category findById(Long categoryId) {
        log.info("finding category by id : {}", categoryId);
        Category category;
        try {
            category = categoryRepository.findById(categoryId).orElse(null);
        } catch (Exception e) {
            log.error("Exception while finding category : ", e);
            category = null;
        }
        return category;
    }
}
