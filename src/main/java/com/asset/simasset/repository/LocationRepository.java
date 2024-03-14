package com.asset.simasset.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.asset.simasset.entity.Location;

import jakarta.transaction.Transactional;

@Repository
public interface LocationRepository extends JpaRepository<Location, String>, JpaSpecificationExecutor<Location>{
    @Query(value = "SELECT * FROM locations l WHERE l.id = ?1" , nativeQuery = true)
    Optional<Location> findLocationById(String id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM locations WHERE id = ?1" , nativeQuery = true)
    void deleteLocationById(String id); 
}
