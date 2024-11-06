package com.example.medicalrecordservice.service;

import com.example.medicalrecordservice.model.DiseaseEntity;

import java.util.List;
import java.util.UUID;

public interface DiseaseService {
    List<DiseaseEntity> getAll(UUID patientId);

    DiseaseEntity create(UUID patientId, DiseaseEntity disease);

    DiseaseEntity update(UUID patientId, DiseaseEntity newDisease);

    void delete(UUID patientId, UUID diseaseId);
}
