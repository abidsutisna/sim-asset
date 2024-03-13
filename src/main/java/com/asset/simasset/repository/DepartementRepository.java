package com.asset.simasset.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.asset.simasset.entity.Department;

@Repository
public interface DepartementRepository extends JpaRepository<Department, String>, JpaSpecificationExecutor<Department>{

} 
