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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asset.simasset.dto.PageResponseWrapper;
import com.asset.simasset.dto.request.SupplierDTO;
import com.asset.simasset.dto.response.ResponseDTO;
import com.asset.simasset.entity.Supplier;
import com.asset.simasset.service.SupplierService;
import com.asset.simasset.utils.constant.ApiUrlConstant;
import com.asset.simasset.utils.constant.MessageConstant;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiUrlConstant.BASE_SUPPLIER)
@RequiredArgsConstructor
public class SupplierController {
    
    private final SupplierService supplierService;

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'MANAGER', 'STAFF')")
    public ResponseEntity<?> getAllSupplier(
            @PageableDefault(page = 0, size = 10, sort = "supplier", direction = Sort.Direction.ASC) Pageable pageable,
            @ModelAttribute SupplierDTO supplierDTO
    ) {
        Page<Supplier> result = supplierService.getAllSupplier(pageable, supplierDTO);
        PageResponseWrapper<Supplier> responseWrapper = new PageResponseWrapper<>(result);

        return ResponseDTO.renderJson(responseWrapper, MessageConstant.SUPPLIER_FOUND, HttpStatus.OK);
    }

    @GetMapping(ApiUrlConstant.PATH_ID)
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'MANAGER', 'STAFF')")
    public ResponseEntity<?> getById(@PathVariable String id) {
        Supplier supplier = supplierService.getSupplierById(id);
        return ResponseDTO.renderJson(supplier, MessageConstant.SUPPLIER_FOUND, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'MANAGER', 'STAFF')")
    public ResponseEntity<?> createSupplier(@RequestBody @Valid SupplierDTO supplierDTO) {
        Supplier supplier = supplierService.create(supplierDTO);
        return ResponseDTO.renderJson(supplier, MessageConstant.SUPPLIER_CREATED, HttpStatus.CREATED);
    }

    @PutMapping(ApiUrlConstant.PATH_ID)
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'MANAGER', 'STAFF')")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody SupplierDTO supplierDTO) {
        Supplier supplier = supplierService.update(id, supplierDTO);
        return ResponseDTO.renderJson(supplier, MessageConstant.SUPPLIER_UPDATED, HttpStatus.OK);
    }

    @DeleteMapping(ApiUrlConstant.PATH_ID)
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'MANAGER')")
    public ResponseEntity<?> delete(@PathVariable String id) {
        supplierService.delete(id);
        return ResponseDTO.renderJson(null, MessageConstant.SUPPLIER_DELETED, HttpStatus.OK);
    }
}
