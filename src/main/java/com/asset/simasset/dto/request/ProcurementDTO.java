package com.asset.simasset.dto.request;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.asset.simasset.utils.ConditionEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProcurementDTO {
    private String asset;

    private MultipartFile image;

    private String merk;

    private String satuan;

    private Double stock;
    
    private Double value;

    private ConditionEnum status;

    private String categoryCode;

    private String supplierId;

    private String locationId;

    private String description;
    
    private String tanggalPembelian;
}
