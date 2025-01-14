package com.example.medicalrecordservice.repository;

import com.example.medicalrecordservice.model.DiseaseEntity;
import com.example.medicalrecordservice.model.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DiseaseRepository extends JpaRepository<DiseaseEntity, UUID> {
    Optional<DiseaseEntity> findDiseaseEntityByPatientEntityAndMkb10CodeAndEndDateOfDiseaseIsNull(PatientEntity patientEntity, String mkb10Code);

    Optional<DiseaseEntity> findDiseaseEntityByIdAndPatientEntity(UUID id, PatientEntity patientEntity);

}
