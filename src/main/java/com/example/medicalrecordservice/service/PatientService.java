package com.example.medicalrecordservice.service;

import com.example.medicalrecordservice.model.PatientEntity;

import java.util.List;
import java.util.UUID;

public interface PatientService {
    List<PatientEntity> getAll();

    PatientEntity create(PatientEntity patient);

    PatientEntity update(PatientEntity newPatient);

    void delete(UUID patientId);
}
