package com.asset.simasset.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asset.simasset.dto.PageResponseWrapper;
import com.asset.simasset.dto.request.UserDTO;
import com.asset.simasset.dto.response.ResponseDTO;
import com.asset.simasset.entity.User;
import com.asset.simasset.service.UserService;
import com.asset.simasset.utils.constant.ApiUrlConstant;
import com.asset.simasset.utils.constant.MessageConstant;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping(ApiUrlConstant.BASE_USER)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'MANAGER')")
    public ResponseEntity<?> getAllUser(
            @PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable,
            @ModelAttribute UserDTO userDTO
    ) {
        Page<User> result = userService.getAllUser(pageable, userDTO);
        PageResponseWrapper<User> responseWrapper = new PageResponseWrapper<>(result);

        return ResponseDTO.renderJson(responseWrapper, MessageConstant.USER_FOUND, HttpStatus.OK);
    }

    @GetMapping(ApiUrlConstant.PATH_ID)
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'MANAGER', 'STAFF')")
    public ResponseEntity<?> getById(@PathVariable String id) {
        User user = userService.loadByUserId(id);
        return ResponseDTO.renderJson(user, MessageConstant.USER_FOUND, HttpStatus.OK);
    }

    @PutMapping(ApiUrlConstant.PATH_ID)
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'MANAGER')")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody UserDTO userDTO) {
        User user = userService.update(id, userDTO);
        return ResponseDTO.renderJson(user, MessageConstant.USER_UPDATED, HttpStatus.OK);
    }

    @DeleteMapping(ApiUrlConstant.PATH_ID)
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'MANAGER')")
    public ResponseEntity<?> delete(@PathVariable String id) {
        userService.delete(id);
        return ResponseDTO.renderJson(null, MessageConstant.USER_DELETED, HttpStatus.OK);
    }
}
