package com.asset.simasset.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.asset.simasset.dto.request.CategoryDTO;
import com.asset.simasset.entity.Category;

public interface CategoryService {
    Page<Category> getAllCategory(Pageable pageable, CategoryDTO req);
    Category create(CategoryDTO req);
    Category getCategoryById(String id);
    void delete(String id);
    Category update(String id, CategoryDTO categoryDTO);
}
