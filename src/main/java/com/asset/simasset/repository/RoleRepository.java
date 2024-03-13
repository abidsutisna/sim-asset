package com.asset.simasset.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.asset.simasset.entity.Role;
import com.asset.simasset.utils.RoleEnum;

@Repository
public interface RoleRepository extends JpaRepository<Role, String>, JpaSpecificationExecutor<Role>{
    Optional<Role> findByRole(RoleEnum role);
}
