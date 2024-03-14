package com.asset.simasset.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.asset.simasset.dto.request.DepartmentDTO;
import com.asset.simasset.entity.Department;

public interface DepartmentService {
    Page<Department> getAllDepartment(Pageable pageable, DepartmentDTO req);
    Department create(DepartmentDTO req);
    Department getDepartmentById(String id);
    void delete(String id);
    Department update(String id, DepartmentDTO departmentDTO);
}
