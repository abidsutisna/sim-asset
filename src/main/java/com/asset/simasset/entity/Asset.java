package com.asset.simasset.entity;

import java.time.LocalDate;
import java.util.Date;

import com.asset.simasset.utils.ConditionEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "assets")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String asset;

    private String image;

    private String merk;

    private String satuan;

    private Double stock;
    
    private Double value;

    @Enumerated(EnumType.STRING)
    private ConditionEnum status;


    @ManyToOne
    @JoinColumn(name ="category_id", referencedColumnName = "categoryCode")
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="supplier_id", referencedColumnName = "id")
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name ="location_id", referencedColumnName = "id")
    private Location location;

    private String description;
    
    @Column(name = "tahun_pembelian")
    private Date tahun_pembelian;

    private LocalDate createdDate;

    private LocalDate updatedDate;

    @PrePersist
    public void prePersist() {
        createdDate = LocalDate.now();
        updatedDate = LocalDate.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedDate = LocalDate.now();
    }
}
