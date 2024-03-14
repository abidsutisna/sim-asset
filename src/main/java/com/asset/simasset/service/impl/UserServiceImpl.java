package com.asset.simasset.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ResponseStatusException;

import com.asset.simasset.dto.request.UserDTO;
import com.asset.simasset.dto.response.JwtClaim;
import com.asset.simasset.entity.User;
import com.asset.simasset.repository.UserRepository;
import com.asset.simasset.security.JwtUtils;
import com.asset.simasset.service.UserService;
import com.asset.simasset.utils.GeneralSpecification;

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
        return userRepository.findUserById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized"));
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

    @Override
    public Page<User> getAllUser(Pageable pageable, UserDTO req) {
        try {
            Specification<User> specification = GeneralSpecification.getSpecification(req);
            return userRepository.findAll(specification, pageable);
        } catch (Exception e) {
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Override
    public void delete(String id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Override
    public User update(String id, UserDTO userDTO) {
        try {

            User user = userRepository.findUserById(id).get();
            user.setEmail((userDTO.getEmail() != null) ? userDTO.getEmail() : user.getEmail());
            user.setName((userDTO.getName() != null) ? userDTO.getName() : user.getName());      
            user.setNoPegawai(userDTO.getNoPegawai());     

            return userRepository.save(user);
        } catch (Exception e) {
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
