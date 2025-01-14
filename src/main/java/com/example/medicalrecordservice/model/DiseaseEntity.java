package com.example.medicalrecordservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "diseases")
public class DiseaseEntity {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private UUID id;

    @NotNull
    @Column(name = "mkb10_code")
    private String mkb10Code;

    @NotNull
    @Column(name = "start_date_of_disease")
    private LocalDate startDateOfDisease;

    @Column(name = "end_date_of_disease")
    private LocalDate endDateOfDisease;

    @NotNull
    @Column(name = "prescription")
    private String prescription;

    @ManyToOne
    @JoinColumn(name = "patients_id")
    private PatientEntity patientEntity;

}
