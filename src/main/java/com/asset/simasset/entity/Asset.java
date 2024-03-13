package com.asset.simasset.entity;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;

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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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

    @Enumerated(EnumType.STRING)
    private ConditionEnum condition;

    private Double value;

    @ManyToOne
    @JoinColumn(name ="category_id", referencedColumnName = "categoryCode")
    private Category category;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "asset_supplier",
            joinColumns = @JoinColumn(name = "asset_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "supplier_id", referencedColumnName = "id")
    )
    private List<Supplier> supplier;

    @ManyToOne
    @JoinColumn(name ="location_id", referencedColumnName = "id")
    private Location location;

    @Enumerated(EnumType.STRING)
    private ConditionEnum conditionEnum;

    private String description;
    
    @Column(name = "tahun_pembelian")
    private Year tahun_pembelian;

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
