package com.asset.simasset.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asset.simasset.dto.PageResponseWrapper;
import com.asset.simasset.dto.request.LocationDTO;
import com.asset.simasset.dto.response.ResponseDTO;
import com.asset.simasset.entity.Location;
import com.asset.simasset.service.LocationService;
import com.asset.simasset.utils.constant.ApiUrlConstant;
import com.asset.simasset.utils.constant.MessageConstant;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiUrlConstant.BASE_LOCATION)
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping
    public ResponseEntity<?> getAllCategory(
            @PageableDefault(page = 0, size = 10, sort = "location", direction = Sort.Direction.ASC) Pageable pageable,
            @ModelAttribute LocationDTO locationDTO
    ) {
        Page<Location> result = locationService.getAllLocation(pageable, locationDTO);
        PageResponseWrapper<Location> responseWrapper = new PageResponseWrapper<>(result);

        return ResponseDTO.renderJson(responseWrapper, MessageConstant.LOCATION_FOUND, HttpStatus.OK);
    }

    @GetMapping(ApiUrlConstant.PATH_ID)
    public ResponseEntity<?> getById(@PathVariable String id) {
        Location location = locationService.getLocationById(id);
        return ResponseDTO.renderJson(location, MessageConstant.LOCATION_FOUND, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createLocation(@RequestBody @Valid LocationDTO locationDTO) {
        Location location = locationService.create(locationDTO);
        return ResponseDTO.renderJson(location, MessageConstant.LOCATION_CREATED, HttpStatus.CREATED);
    }

    @PutMapping(ApiUrlConstant.PATH_ID)
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody LocationDTO categoryDTO) {
        Location location = locationService.update(id, categoryDTO);
        return ResponseDTO.renderJson(location, MessageConstant.LOCATION_CREATED, HttpStatus.OK);
    }

    @DeleteMapping(ApiUrlConstant.PATH_ID)
    public ResponseEntity<?> delete(@PathVariable String id) {
        locationService.delete(id);
        return ResponseDTO.renderJson(null, MessageConstant.CATEGORY_DELETED, HttpStatus.OK);
    }
}
