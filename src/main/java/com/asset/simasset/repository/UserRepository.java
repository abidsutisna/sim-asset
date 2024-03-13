package com.asset.simasset.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.asset.simasset.entity.User;

@Repository
public interface UserRepository  extends JpaRepository<User, String>, JpaSpecificationExecutor<User>{
    Optional<User> findByEmail(String email);
}
