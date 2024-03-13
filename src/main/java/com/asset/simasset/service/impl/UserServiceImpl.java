package com.asset.simasset.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.asset.simasset.dto.response.JwtClaim;
import com.asset.simasset.entity.User;
import com.asset.simasset.repository.UserRepository;
import com.asset.simasset.security.JwtUtils;
import com.asset.simasset.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    private final JwtUtils jwt;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized"));
    }

    @Override
    public User loadByUserId(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized"));
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserDetails(String token) {
        String parsedToken = null;
        if(token != null && token.startsWith("Bearer ")) {
            parsedToken = token.substring(7);
        }

        JwtClaim user = jwt.getUserInfoByToken(parsedToken);
        return loadByUserId(user.getUserId());
    }
}
