package com.asset.simasset.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.MultipartFile;

import com.asset.simasset.dto.request.ProcurementDTO;
import com.asset.simasset.entity.Asset;
import com.asset.simasset.entity.Category;
import com.asset.simasset.entity.Location;
import com.asset.simasset.entity.Supplier;
import com.asset.simasset.repository.AssetRepository;
import com.asset.simasset.service.AssetService;
import com.asset.simasset.service.CategoryService;
import com.asset.simasset.service.LocationService;
import com.asset.simasset.service.SupplierService;
import com.asset.simasset.utils.GeneralSpecification;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssetServiceImpl implements AssetService {

    private final AssetRepository assetRepository;

    private final Cloudinary cloudinary;

    private final CategoryService categoryService;

    private final SupplierService supplierService;

    private final LocationService locationService;
    
    @Override
    public Page<Asset> getAllAsset(Pageable pageable, ProcurementDTO req) {
        try {
            Specification<Asset> specification = GeneralSpecification.getSpecification(req);
            return assetRepository.findAll(specification, pageable);
        } catch (Exception e) {
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Override
    public Asset create(ProcurementDTO req) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Map<?,?> result = upload(req.getImage());

            Asset asset = new Asset();
            asset.setAsset(req.getAsset());
            asset.setImage(result.get("url").toString());
            asset.setMerk(req.getMerk());
            asset.setSatuan(req.getSatuan());
            asset.setValue(req.getValue());
            asset.setStock(req.getStock());
            asset.setStatus(req.getStatus());

            Date date = dateFormat.parse(req.getTanggalPembelian());
            asset.setTahun_pembelian(date);

            Category category = categoryService.getCategoryById(req.getCategoryCode());
            asset.setCategory(category);

            Supplier supplier = supplierService.getSupplierById(req.getSupplierId()); 
            asset.setSupplier(supplier);

            Location location = locationService.getLocationById(req.getLocationId());
            asset.setLocation(location);

            asset.setDescription(req.getDescription());
            
            return assetRepository.save(asset);
        } catch (Exception e) {
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    private Map<?, ?> upload(MultipartFile multipartFile) throws IOException{
        Map<?, ?> result = cloudinary.uploader().upload(multipartFile.getBytes(), ObjectUtils.emptyMap());
        return result;
    }

    @Override
    public Asset getAssetById(String id) {
        try {
            Asset asset = assetRepository.findById(id).get();
            return asset;
        } catch (Exception e) {
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Override
    public void delete(String id) {
        try {
            assetRepository.deleteById(id);
        } catch (Exception e) {
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Override
    public Asset update(String id, ProcurementDTO req) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Asset asset = assetRepository.findById(id).get();

            Map<?,?> result = ((req.getImage() != null) ? upload(req.getImage()) : Collections.emptyMap());

            asset.setAsset((req.getAsset() != null) ? req.getAsset() : asset.getAsset());
            asset.setImage((req.getImage() != null) ? result.get("url").toString() : asset.getImage());
            asset.setMerk((req.getMerk() != null) ? req.getMerk() : asset.getMerk());
            asset.setSatuan((req.getSatuan() != null) ? req.getSatuan() : asset.getSatuan());
            asset.setValue((req.getValue() != null) ? req.getValue() : asset.getValue());
            asset.setStock((req.getStock() != null) ? req.getStock() : asset.getStock());
            asset.setStatus((req.getStatus() != null) ? req.getStatus() : asset.getStatus());

            Date date = dateFormat.parse(req.getTanggalPembelian());
            asset.setTahun_pembelian((req.getTanggalPembelian() != null) ? date : asset.getTahun_pembelian());

            Category category = categoryService.getCategoryById(req.getCategoryCode());
            asset.setCategory((req.getCategoryCode() != null) ? category : asset.getCategory());

            Supplier supplier = supplierService.getSupplierById(req.getSupplierId()); 
            asset.setSupplier((req.getSupplierId() != null) ? supplier : asset.getSupplier());

            Location location = locationService.getLocationById(req.getLocationId());
            asset.setLocation((req.getLocationId()!= null) ? location : asset.getLocation());

            asset.setDescription((req.getDescription() != null) ? req.getDescription() : asset.getDescription());
            
            return assetRepository.save(asset);
        } catch (Exception e) {
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    
}
