package com.asset.simasset.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.asset.simasset.entity.User;

public interface UserService extends UserDetailsService{
    User loadByUserId(String userId);
    User createUser(User user);
    User getUserDetails(String token);
}
