package com.asset.simasset.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.asset.simasset.dto.request.ProcurementDTO;
import com.asset.simasset.entity.Asset;

public interface AssetService {
    Page<Asset> getAllAsset(Pageable pageable, ProcurementDTO req);
    Asset create(ProcurementDTO req);
    Asset getAssetById(String id);
    void delete(String id);
    Asset update(String id, ProcurementDTO req);
}