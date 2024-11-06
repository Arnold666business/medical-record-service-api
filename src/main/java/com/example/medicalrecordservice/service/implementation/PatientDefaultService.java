package com.example.medicalrecordservice.service.implementation;

import com.example.medicalrecordservice.exception.patient.DuplicateOmsPolicyException;
import com.example.medicalrecordservice.helper.PatientHelper;
import com.example.medicalrecordservice.model.PatientEntity;
import com.example.medicalrecordservice.repository.PatientRepository;
import com.example.medicalrecordservice.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientDefaultService implements PatientService {
    private final PatientRepository patientRepository;

    private final PatientHelper patientHelper;

    @Override
    @Transactional(readOnly = true)
    public List<PatientEntity> getAll() {
        return patientRepository.findAll();
    }

    @Override
    @Transactional
    public PatientEntity create(PatientEntity patient) {
        if(patientRepository.existsByOmsNumber(patient.getOmsNumber())){
            throw new DuplicateOmsPolicyException(patient.getOmsNumber());
        }
        return patientRepository.save(patient);
    }

    @Override
    @Transactional
    public PatientEntity update(PatientEntity newPatient) {
        PatientEntity patient = patientHelper.findByIdOrThrow(newPatient.getId());
        if(patient.equalsByFields(newPatient)){
            return patient;
        }
        newPatient.setDiseaseEntities(patient.getDiseaseEntities());
        return patientRepository.save(newPatient);
    }

    @Override
    @Transactional
    public void delete(UUID patientId) {
        PatientEntity patient = patientHelper.findByIdOrThrow(patientId);
        patientRepository.deleteById(patientId);
    }
}
