package com.asset.simasset.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.asset.simasset.entity.Department;

import jakarta.transaction.Transactional;


@Repository
public interface DepartementRepository extends JpaRepository<Department, String>, JpaSpecificationExecutor<Department>{
    @Query(value = "SELECT * FROM departments d WHERE d.id = ?1" , nativeQuery = true)
    Optional<Department> findDepartmentById(String id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM departments WHERE id = ?1" , nativeQuery = true)
    void deleteDepartmentById(String id);    
} 
