package com.asset.simasset.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "locations")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Location {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String location;

    @ManyToOne
    @JoinColumn(name ="department_id")
    @JsonBackReference
    private Department department;

    @OneToMany(mappedBy = "location")
    @JsonBackReference
    private List<Asset> asset;
}
