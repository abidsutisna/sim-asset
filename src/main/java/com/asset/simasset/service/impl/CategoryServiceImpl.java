package com.asset.simasset.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import com.asset.simasset.dto.request.CategoryDTO;
import com.asset.simasset.entity.Category;
import com.asset.simasset.repository.CategoryRepository;
import com.asset.simasset.service.CategoryService;
import com.asset.simasset.utils.GeneralSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    
    private final CategoryRepository categoryRepository;

    @Override
    public Page<Category> getAllCategory(Pageable pageable, CategoryDTO req) {
       try {
            Specification<Category> specification = GeneralSpecification.getSpecification(req);
            return categoryRepository.findAll(specification, pageable);
        } catch (Exception e) {
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Override
    public Category create(CategoryDTO req) {
         try {

            Category category = new Category();
            category.setCategory(req.getCategory());
            
            return categoryRepository.save(category);
        } catch (Exception e) {
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public Category getCategoryById(String id) {
        try {
            Category category = categoryRepository.findCategoryById(id).get();
            return category;
        } catch (Exception e) {
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Override
    public void delete(String id) {
        try {
            categoryRepository.deleteCategoryById(id);
        } catch (Exception e) {
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Override
    public Category update(String id, CategoryDTO categoryDTO) {
        try {
            Category category = categoryRepository.findCategoryById(id).get();

            category.setCategory((categoryDTO.getCategory() != null) ? categoryDTO.getCategory() : category.getCategory());
            return categoryRepository.save(category);
        } catch (Exception e) {
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    
}
