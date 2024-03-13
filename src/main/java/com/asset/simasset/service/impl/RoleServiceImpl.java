package com.asset.simasset.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.asset.simasset.entity.Role;
import com.asset.simasset.repository.RoleRepository;
import com.asset.simasset.service.RoleService;
import com.asset.simasset.utils.RoleEnum;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{
    
    private final RoleRepository roleRepository;

    @Override
    public Role getOrSave(RoleEnum role) {
        Optional<Role> optionalRole = roleRepository.findByRole(role);
       if(optionalRole.isPresent()) {
           return optionalRole.get();
       }

       Role newRole = Role.builder().role(role).build();
       return roleRepository.save(newRole);
    }
}
