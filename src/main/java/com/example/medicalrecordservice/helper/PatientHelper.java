package com.example.medicalrecordservice.helper;

import com.example.medicalrecordservice.exception.patient.NotFoundPatientException;
import com.example.medicalrecordservice.model.PatientEntity;
import com.example.medicalrecordservice.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PatientHelper {
    private final PatientRepository patientRepository;

    @Transactional(readOnly = true)
    public PatientEntity findByIdOrThrow(UUID patientId){
        return patientRepository.findById(patientId)
                .orElseThrow(() -> new NotFoundPatientException(patientId));
    }

    public void save(PatientEntity patient){
        patientRepository.save(patient);
    }
}
