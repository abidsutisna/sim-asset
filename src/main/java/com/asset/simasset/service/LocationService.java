package com.asset.simasset.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.asset.simasset.dto.request.LocationDTO;
import com.asset.simasset.entity.Location;

public interface LocationService {
    Page<Location> getAllLocation(Pageable pageable, LocationDTO req);
    Location create(LocationDTO req);
    Location getLocationById(String id);
    void delete(String id);
    Location update(String id, LocationDTO locationDTO);
}
