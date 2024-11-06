package com.example.medicalrecordservice.helper;

import com.example.medicalrecordservice.exception.disease.NotFoundDiseaseException;
import com.example.medicalrecordservice.model.DiseaseEntity;
import com.example.medicalrecordservice.model.PatientEntity;
import com.example.medicalrecordservice.repository.DiseaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DiseaseHelper {
    private final DiseaseRepository diseaseRepository;

    @Transactional(readOnly = true)
    public DiseaseEntity findDiseaseByIdAndPatient(UUID diseaseId, PatientEntity patient){
        return diseaseRepository
                .findDiseaseEntityByIdAndPatientEntity(diseaseId, patient)
                .orElseThrow(() -> new NotFoundDiseaseException(patient.getId(), diseaseId));
    }
}
