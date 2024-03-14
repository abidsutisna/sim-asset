package com.asset.simasset.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import com.asset.simasset.dto.request.SupplierDTO;
import com.asset.simasset.entity.Supplier;
import com.asset.simasset.repository.SupplierRepository;
import com.asset.simasset.service.SupplierService;
import com.asset.simasset.utils.GeneralSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {
    
    private final SupplierRepository supplierRepository;

    @Override
    public Page<Supplier> getAllSupplier(Pageable pageable, SupplierDTO req) {
        try {
            Specification<Supplier> specification = GeneralSpecification.getSpecification(req);
            return supplierRepository.findAll(specification, pageable);
        } catch (Exception e) {
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Override
    public Supplier create(SupplierDTO req) {
        try {
            Supplier supplier = new Supplier();
            supplier.setAlamat(req.getAlamat());
            supplier.setNoHp(req.getNoHp());
            supplier.setSupplier(req.getSupplier());

            return supplierRepository.save(supplier);
        } catch (Exception e) {
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public Supplier getSupplierById(String id) {
        try {
            Supplier supplier = supplierRepository.findById(id).get();
            return supplier;
        } catch (Exception e) {
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Override
    public void delete(String id) {
        try {
            supplierRepository.deleteById(id);
        } catch (Exception e) {
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Override
    public Supplier update(String id, SupplierDTO supplierDTO) {
        try {
            Supplier supplier = supplierRepository.findById(id).get();
            supplier.setAlamat((supplierDTO.getAlamat() != null) ? supplierDTO.getAlamat() : supplier.getAlamat());
            supplier.setNoHp((supplierDTO.getNoHp() != null) ? supplierDTO.getNoHp() : supplier.getNoHp());
            supplier.setSupplier((supplierDTO.getSupplier() != null) ? supplierDTO.getSupplier() : supplier.getSupplier());

            return supplierRepository.save(supplier);
        } catch (Exception e) {
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    
}
