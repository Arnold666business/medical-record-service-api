package com.example.medicalrecordservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "mkb10_dictionary")
public class Mkb10DiseaseInfoEntity {
    @Id
    private String code;

    @NotNull
    @Column(name = "disease_name")
    private String diseaseName;
}
