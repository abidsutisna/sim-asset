package com.asset.simasset.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.asset.simasset.entity.Supplier;

import jakarta.transaction.Transactional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, String>, JpaSpecificationExecutor<Supplier>{
    @Query(value = "SELECT * FROM suppliers s WHERE s.id = ?1" , nativeQuery = true)
    Optional<Supplier> findSupplierById(String id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM suppliers WHERE id = ?1" , nativeQuery = true)
    void deleteSupplierById(String id); 
}
