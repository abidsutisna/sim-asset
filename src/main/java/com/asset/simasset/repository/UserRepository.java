package com.asset.simasset.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.asset.simasset.entity.User;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepository  extends JpaRepository<User, String>, JpaSpecificationExecutor<User>{
    
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT * FROM users u WHERE u.id = ?1" , nativeQuery = true)
    Optional<User> findUserById(String id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM users WHERE id = ?1" , nativeQuery = true)
    void deleteUserById(String id); 
}
