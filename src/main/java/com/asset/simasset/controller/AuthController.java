package com.asset.simasset.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asset.simasset.dto.request.AuthRequest;
import com.asset.simasset.dto.request.RegisterDTO;
import com.asset.simasset.dto.response.ResponseDTO;
import com.asset.simasset.dto.response.UserResponseDTO;
import com.asset.simasset.service.AuthService;
import com.asset.simasset.utils.constant.ApiUrlConstant;
import com.asset.simasset.utils.constant.MessageConstant;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiUrlConstant.BASE_AUTH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'MANAGER')")
    @PostMapping(ApiUrlConstant.REGISTER)
    public ResponseEntity<?> register (@RequestBody @Valid RegisterDTO registerDTO){
        UserResponseDTO userResponse = authService.register(registerDTO);
        return ResponseDTO.renderJson(userResponse, MessageConstant.REGISTER_SUCCESS, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    @PostMapping(ApiUrlConstant.REGISTER_MANAGER)
    public ResponseEntity<?> registerManager(@RequestBody @Valid RegisterDTO registerDTO){
        UserResponseDTO userResponse = authService.registerManager(registerDTO);
        return ResponseDTO.renderJson(userResponse, MessageConstant.REGISTER_SUCCESS, HttpStatus.CREATED);
    }

    @PostMapping(ApiUrlConstant.LOGIN)
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest authRequest){
        String token = authService.login(authRequest.getEmail(), authRequest.getPassword());
        return ResponseDTO.renderJson(token, MessageConstant.LOGIN_SUCCESS, HttpStatus.ACCEPTED);
    }
}
