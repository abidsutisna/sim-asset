package com.asset.simasset.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asset.simasset.dto.PageResponseWrapper;
import com.asset.simasset.dto.request.CategoryDTO;
import com.asset.simasset.dto.response.ResponseDTO;
import com.asset.simasset.entity.Category;
import com.asset.simasset.service.CategoryService;
import com.asset.simasset.utils.constant.ApiUrlConstant;
import com.asset.simasset.utils.constant.MessageConstant;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiUrlConstant.BASE_CATEGORY)
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'MANAGER', 'STAFF')")
    public ResponseEntity<?> getAllCategory(
            @PageableDefault(page = 0, size = 10, sort = "category", direction = Sort.Direction.ASC) Pageable pageable,
            @ModelAttribute CategoryDTO categoryDTO
    ) {
        Page<Category> result = categoryService.getAllCategory(pageable, categoryDTO);
        PageResponseWrapper<Category> responseWrapper = new PageResponseWrapper<>(result);

        return ResponseDTO.renderJson(responseWrapper, MessageConstant.CATEGORY_FOUND, HttpStatus.OK);
    }

    @GetMapping(ApiUrlConstant.PATH_ID)
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'MANAGER', 'STAFF')")
    public ResponseEntity<?> getById(@PathVariable String id) {
        Category category = categoryService.getCategoryById(id);
        return ResponseDTO.renderJson(category, MessageConstant.CATEGORY_FOUND, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'MANAGER', 'STAFF')")
    public ResponseEntity<?> createCategory(@RequestBody @Valid CategoryDTO categoryDTO) {
        Category category = categoryService.create(categoryDTO);
        return ResponseDTO.renderJson(category, MessageConstant.CATEGORY_CREATED, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'MANAGER', 'STAFF')")
    @PutMapping(ApiUrlConstant.PATH_ID)
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody CategoryDTO categoryDTO) {
        Category updateCategory = categoryService.update(id, categoryDTO);
        return ResponseDTO.renderJson(updateCategory, MessageConstant.CATEGORY_UPDATED, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'MANAGER', 'STAFF')")
    @DeleteMapping(ApiUrlConstant.PATH_ID)
    public ResponseEntity<?> delete(@PathVariable String id) {
        categoryService.delete(id);
        return ResponseDTO.renderJson(null, MessageConstant.CATEGORY_DELETED, HttpStatus.OK);
    }
}
