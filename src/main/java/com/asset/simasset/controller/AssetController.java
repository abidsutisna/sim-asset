package com.asset.simasset.controller;

import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.asset.simasset.dto.PageResponseWrapper;
import com.asset.simasset.dto.request.ProcurementDTO;
import com.asset.simasset.dto.response.ResponseDTO;
import com.asset.simasset.entity.Asset;
import com.asset.simasset.service.AssetService;
import com.asset.simasset.utils.constant.ApiUrlConstant;
import com.asset.simasset.utils.constant.MessageConstant;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiUrlConstant.BASE_ASSET)
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;
    

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'MANAGER', 'STAFF')")
    public ResponseEntity<?> getAllAsset(
            @PageableDefault(page = 0, size = 10, sort = "asset", direction = Sort.Direction.ASC) Pageable pageable,
            @ModelAttribute ProcurementDTO procurementDTO
    ) {
        Page<Asset> result = assetService.getAllAsset(pageable, procurementDTO);
        PageResponseWrapper<Asset> responseWrapper = new PageResponseWrapper<>(result);

        return ResponseDTO.renderJson(responseWrapper, MessageConstant.ASSET_FOUND, HttpStatus.OK);
    }

    @GetMapping(ApiUrlConstant.PATH_ID)
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'MANAGER', 'STAFF')")
    public ResponseEntity<?> getById(@PathVariable String id) {
        Asset asset = assetService.getAssetById(id);
        return ResponseDTO.renderJson(asset, MessageConstant.ASSET_FOUND, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'MANAGER')")
    @PostMapping(
        consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<?> procurement(@ModelAttribute @Valid ProcurementDTO procurementDTO) {
        Asset asset = assetService.create(procurementDTO);
        return ResponseDTO.renderJson(asset, MessageConstant.ASSET_CREATED, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'MANAGER', 'STAFF')")
    @PutMapping(
        path = ApiUrlConstant.PATH_ID,
        consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody ProcurementDTO procurementDTO) {
        Asset updateAsset = assetService.update(id, procurementDTO);
        return ResponseDTO.renderJson(updateAsset, MessageConstant.ASSET_UPDATED, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'MANAGER', 'STAFF')")
    @DeleteMapping(ApiUrlConstant.PATH_ID)
    public ResponseEntity<?> delete(@PathVariable String id) {
        assetService.delete(id);
        return ResponseDTO.renderJson(null, MessageConstant.ASSET_DELETED, HttpStatus.OK);
    }
}
