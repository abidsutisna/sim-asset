package com.asset.simasset.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import com.asset.simasset.dto.request.DepartmentDTO;
import com.asset.simasset.entity.Department;
import com.asset.simasset.repository.DepartementRepository;
import com.asset.simasset.service.DepartmentService;
import com.asset.simasset.utils.GeneralSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartementRepository departmentRepository;

    @Override
    public Page<Department> getAllDepartment(Pageable pageable, DepartmentDTO req) {
       try {
            Specification<Department> specification = GeneralSpecification.getSpecification(req);
            return departmentRepository.findAll(specification, pageable);
        } catch (Exception e) {
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Override
    public Department create(DepartmentDTO req) {
        try {
            Department department = new Department();
            department.setDepartment(req.getDepartment());
            department.setDescription(req.getDescription());

            return departmentRepository.save(department);
        } catch (Exception e) {
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public Department getDepartmentById(String id) {
        try {
            Department department = departmentRepository.findDepartmentById(id).get();
            return department;
        } catch (Exception e) {
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Override
    public void delete(String id) {
        try {
            departmentRepository.deleteDepartmentById(id);
        } catch (Exception e) {
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Override
    public Department update(String id, DepartmentDTO departmentDTO) {
        try {
            Department department = departmentRepository.findDepartmentById(id).get();
            department.setDepartment((departmentDTO.getDepartment() != null) ? departmentDTO.getDepartment() : department.getDepartment());
            department.setDescription((departmentDTO.getDepartment() != null) ? departmentDTO.getDescription() : department.getDescription());

            return departmentRepository.save(department);
        } catch (Exception e) {
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    
}
