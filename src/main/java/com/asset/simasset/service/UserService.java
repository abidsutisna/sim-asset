package com.asset.simasset.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.asset.simasset.dto.request.UserDTO;
import com.asset.simasset.entity.User;

public interface UserService extends UserDetailsService{
    User loadByUserId(String userId);
    User getUserDetails(String token);
    Page<User> getAllUser(Pageable pageable, UserDTO req);;
    void delete(String id);
    User update(String id, UserDTO userDTO);
}
