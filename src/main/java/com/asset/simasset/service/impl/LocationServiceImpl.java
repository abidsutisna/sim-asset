package com.asset.simasset.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import com.asset.simasset.dto.request.LocationDTO;
import com.asset.simasset.entity.Department;
import com.asset.simasset.entity.Location;
import com.asset.simasset.repository.LocationRepository;
import com.asset.simasset.service.DepartmentService;
import com.asset.simasset.service.LocationService;
import com.asset.simasset.utils.GeneralSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    
    private final LocationRepository locationRepository;

    private final DepartmentService departmentService;

    @Override
    public Page<Location> getAllLocation(Pageable pageable, LocationDTO req) {
       try {
            Specification<Location> specification = GeneralSpecification.getSpecification(req);
            return locationRepository.findAll(specification, pageable);
        } catch (Exception e) {
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Override
    public Location create(LocationDTO req) {
        try {
            Location location = new Location();
            location.setLocation(req.getLocation());

            Department department = departmentService.getDepartmentById(req.getDepartment());
            location.setDepartment(department);

            return locationRepository.save(location);
        } catch (Exception e) {
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public Location getLocationById(String id) {
        try {
            Location location = locationRepository.findById(id).get();
            return location;
        } catch (Exception e) {
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Override
    public void delete(String id) {
        try {
            locationRepository.deleteById(id);
        } catch (Exception e) {
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Override
    public Location update(String id, LocationDTO locationDTO) {
        try {
            Location location = locationRepository.findById(id).get();

            location.setLocation((locationDTO.getLocation() != null) ? locationDTO.getLocation() : location.getLocation());

            Department department = departmentService.getDepartmentById(locationDTO.getDepartment());
            location.setDepartment((locationDTO.getDepartment() != null) ? department : location.getDepartment());
            return locationRepository.save(location);
        } catch (Exception e) {
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    
}
