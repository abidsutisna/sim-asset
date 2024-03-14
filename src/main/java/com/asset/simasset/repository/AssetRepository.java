package com.asset.simasset.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.asset.simasset.entity.Asset;

import jakarta.transaction.Transactional;

@Repository
public interface AssetRepository extends JpaRepository<Asset, String>, JpaSpecificationExecutor<Asset>{
    @Query(value = "SELECT * FROM assets a WHERE a.id = ?1" , nativeQuery = true)
    Optional<Asset> findAssetById(String id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM assets WHERE id = ?1" , nativeQuery = true)
    void deleteAssetById(String id); 
} 
