package com.asset.simasset.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.asset.simasset.dto.request.SupplierDTO;
import com.asset.simasset.entity.Supplier;

public interface SupplierService {
    Page<Supplier> getAllSupplier(Pageable pageable, SupplierDTO req);
    Supplier create(SupplierDTO req);
    Supplier getSupplierById(String id);
    void delete(String id);
    Supplier update(String id, SupplierDTO supplierDTO);
} 
