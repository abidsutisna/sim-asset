package com.asset.simasset.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.asset.simasset.entity.Category;

import jakarta.transaction.Transactional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String>, JpaSpecificationExecutor<Category>{

    @Query(value = "SELECT * FROM categories c WHERE c.categoryCode = ?1" , nativeQuery = true)
    Optional<Category> findCategoryById(String id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM categories WHERE id = ?1" , nativeQuery = true)
    void deleteCategoryById(String id); 
}
