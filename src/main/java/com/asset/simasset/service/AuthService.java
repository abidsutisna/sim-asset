package com.asset.simasset.service;

import com.asset.simasset.dto.request.RegisterDTO;
import com.asset.simasset.dto.response.UserResponseDTO;

public interface AuthService {
    public String login(String email, String password);
    UserResponseDTO register(RegisterDTO registerDTO);
    UserResponseDTO registerManager(RegisterDTO registerDTO);
}
