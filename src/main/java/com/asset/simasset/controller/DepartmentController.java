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
import com.asset.simasset.dto.request.DepartmentDTO;
import com.asset.simasset.dto.response.ResponseDTO;
import com.asset.simasset.entity.Department;
import com.asset.simasset.service.DepartmentService;
import com.asset.simasset.utils.constant.ApiUrlConstant;
import com.asset.simasset.utils.constant.MessageConstant;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiUrlConstant.BASE_DEPARTMENT)
@RequiredArgsConstructor
public class DepartmentController {
    
    private final DepartmentService departmentService;

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'MANAGER', 'STAFF')")
    public ResponseEntity<?> getAllDepartment(
            @PageableDefault(page = 0, size = 10, sort = "department", direction = Sort.Direction.ASC) Pageable pageable,
            @ModelAttribute DepartmentDTO departmentDTO
    ) {
        Page<Department> result = departmentService.getAllDepartment(pageable, departmentDTO); 
        PageResponseWrapper<Department> responseWrapper = new PageResponseWrapper<>(result);

        return ResponseDTO.renderJson(responseWrapper, MessageConstant.DEPARTMENT_FOUND, HttpStatus.OK);
    }

    @GetMapping(ApiUrlConstant.PATH_ID)
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'MANAGER', 'STAFF')")
    public ResponseEntity<?> getById(@PathVariable String id) {
        Department department = departmentService.getDepartmentById(id);
        return ResponseDTO.renderJson(department, MessageConstant.DEPARTMENT_FOUND, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'MANAGER')")
    public ResponseEntity<?> createDepartment(@RequestBody @Valid DepartmentDTO departmentDTO) {
        Department department = departmentService.create(departmentDTO);
        return ResponseDTO.renderJson(department, MessageConstant.DEPARTMENT_CREATED, HttpStatus.CREATED);
    }

    @PutMapping(ApiUrlConstant.PATH_ID)
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'MANAGER', 'STAFF')")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody DepartmentDTO departmentDTO) {
        Department department = departmentService.update(id, departmentDTO);
        return ResponseDTO.renderJson(department, MessageConstant.DEPARTMENT_UPDATED, HttpStatus.OK);
    }

    @DeleteMapping(ApiUrlConstant.PATH_ID)
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'MANAGER')")
    public ResponseEntity<?> delete(@PathVariable String id) {
        departmentService.delete(id);
        return ResponseDTO.renderJson(null, MessageConstant.DEPARTMENT_DELETED, HttpStatus.OK);
    }
}
