package com.example.medicalrecordservice.service.implementation;

import com.example.medicalrecordservice.exception.patient.DuplicateOmsPolicyException;
import com.example.medicalrecordservice.exception.patient.NotFoundPatientException;
import com.example.medicalrecordservice.model.PatientEntity;
import com.example.medicalrecordservice.repository.PatientRepository;
import com.example.medicalrecordservice.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientDefaultService implements PatientService {
    private final PatientRepository patientRepository;

    private final ApplicationContext applicationContext;

    @Override
    @Transactional(readOnly = true)
    public List<PatientEntity> getAll() {
        return patientRepository.findAll();
    }

    @Override
    @Transactional
    public PatientEntity create(PatientEntity patient) {
        if (patientRepository.existsByOmsNumber(patient.getOmsNumber())) {
            throw new DuplicateOmsPolicyException(patient.getOmsNumber());
        }
        return patientRepository.save(patient);
    }

    @Override
    @Transactional
    public PatientEntity update(PatientEntity newPatient) {
        PatientService proxy = applicationContext.getBean(PatientService.class);
        PatientEntity patient = proxy.findByIdOrThrow(newPatient.getId());
        BeanUtils.copyProperties(newPatient, patient, "id", "diseaseEntities");
        return patient;
    }

    @Override
    @Transactional
    public void delete(UUID patientId) {
        patientRepository.deleteById(patientId);
    }

    @Transactional(readOnly = true)
    public PatientEntity findByIdOrThrow(UUID patientId) {
        return patientRepository.findById(patientId).orElseThrow(() -> new NotFoundPatientException(patientId));
    }

}
