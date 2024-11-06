package com.example.medicalrecordservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "patients")
public class PatientEntity {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private UUID id;

    @NotNull
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @NotNull
    @Column(name = "gender")
    private String gender;

    @NotNull
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @NotNull
    @Column(name = "oms_number", unique = true)
    private String omsNumber;

    @NotNull
    @OneToMany(mappedBy = "patientEntity", orphanRemoval = true)
    private List<DiseaseEntity> diseaseEntities = new ArrayList<>();

    public void addDisease(DiseaseEntity disease){
        this.diseaseEntities.add(disease);
    }

    public void removeDisease(DiseaseEntity disease){
        this.diseaseEntities.remove(disease);
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || o.getClass() != this.getClass()) return false;
        return Objects.equals(this.getId(), ((PatientEntity) o).getId());
    }

    @Override
    public int hashCode(){
        return Objects.hash(id);
    }

    public boolean equalsByFields(PatientEntity patient){
        return Objects.equals(this.getFirstName(), patient.getFirstName()) &&
                Objects.equals(this.getLastName(), patient.getLastName()) &&
                Objects.equals(this.getMiddleName(), patient.getMiddleName()) &&
                Objects.equals(this.getGender(), patient.getGender()) &&
                Objects.equals(this.getBirthDate(), patient.getBirthDate()) &&
                Objects.equals(this.getOmsNumber(), patient.getOmsNumber());
    }
}
