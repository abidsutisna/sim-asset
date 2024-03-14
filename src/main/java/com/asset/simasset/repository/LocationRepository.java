package com.asset.simasset.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.asset.simasset.entity.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, String>, JpaSpecificationExecutor<Location>{
    
}
