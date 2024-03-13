package com.asset.simasset.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.asset.simasset.dto.request.RegisterDTO;
import com.asset.simasset.dto.response.UserResponseDTO;
import com.asset.simasset.entity.Role;
import com.asset.simasset.entity.User;
import com.asset.simasset.repository.UserRepository;
import com.asset.simasset.security.JwtUtils;
import com.asset.simasset.service.AuthService;
import com.asset.simasset.service.RoleService;
import com.asset.simasset.utils.RoleEnum;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepo;

    private final RoleService roleService;

    private final JwtUtils jwtUtils;

    private final AuthenticationManager authenticationManager;

    @PostConstruct
    public void initSuperAdmin(){
        String email = "superadmin@gmail.com";
        String password = "superadmin";

        Optional<User> OptionalUserCredential = userRepo.findByEmail(email);
        if(OptionalUserCredential.isPresent()){
            return;
        }

        Role roleSuperAdmin = roleService.getOrSave(RoleEnum.ROLE_SUPER_ADMIN);
        Role roleAdmin = roleService.getOrSave(RoleEnum.ROLE_MANAGER);
        Role roleCustomer = roleService.getOrSave(RoleEnum.ROLE_STAFF);

        String hashPassword = passwordEncoder.encode(password);
        
        User userCredential = User.builder()
            .email(email)
            .password(hashPassword)
            .roles(List.of(roleSuperAdmin, roleAdmin, roleCustomer))
            .build();
            
        userRepo.saveAndFlush(userCredential);
    }
    
    @Override
    public String login(String email, String password) {
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        
        //save session
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User userCredential = (User) authentication.getPrincipal();

        try {
            return jwtUtils.generateToken(userCredential);
            
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "login failed", e.getCause());
        }
    }

    @Override
    public UserResponseDTO register(RegisterDTO registerDTO) {
        Role roleEmployee = roleService.getOrSave(RoleEnum.ROLE_STAFF);
        String hashPassword = passwordEncoder.encode(registerDTO.getPassword());
        User userCredential = User.builder()
            .email(registerDTO.getEmail())
            .password(hashPassword)
            .name(registerDTO.getName())
            .roles(List.of(roleEmployee))
            .build();
        
        try {
            userRepo.saveAndFlush(userCredential);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "failed to register", e.getCause());
        }

        List<String> roles = userCredential.getRoles().stream().map(role -> role.getRole().name()).toList();
        return UserResponseDTO.builder()
            .email(registerDTO.getEmail())
            .roles(roles)
            .build();
    }

    @Override
    public UserResponseDTO registerManager(RegisterDTO registerDTO) {
        Role roleEmployee = roleService.getOrSave(RoleEnum.ROLE_MANAGER);
        Role roleAdmin = roleService.getOrSave(RoleEnum.ROLE_STAFF);

        String hashPassword = passwordEncoder.encode(registerDTO.getPassword());

        User userCredential = User.builder()
            .email(registerDTO.getEmail())
            .password(hashPassword)
            .name(registerDTO.getName())
            .roles(List.of(roleEmployee, roleAdmin))
            .build();

        try {
            userRepo.saveAndFlush(userCredential);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "failed to register", e.getCause());
        }

        List<String> roles = userCredential.getRoles().stream().map(role -> role.getRole().name()).toList();
        return UserResponseDTO.builder()
            .email(userCredential.getEmail())
            .roles(roles)
            .build();
    }
    
}
