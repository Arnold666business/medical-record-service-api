package com.example.medicalrecordservice.service.implementation;

import com.example.medicalrecordservice.exception.disease.ActiveDiseaseExistsException;
import com.example.medicalrecordservice.exception.disease.NotFoundDiseaseException;
import com.example.medicalrecordservice.model.DiseaseEntity;
import com.example.medicalrecordservice.model.PatientEntity;
import com.example.medicalrecordservice.repository.DiseaseRepository;
import com.example.medicalrecordservice.service.DiseaseService;
import com.example.medicalrecordservice.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DiseaseDefaultService implements DiseaseService {
    private final static Logger logger = LoggerFactory.getLogger(DiseaseDefaultService.class);

    private final DiseaseRepository diseaseRepository;

    private final PatientService patientService;

    private final ApplicationContext applicationContext;

    private DiseaseService getDiseaseService() {
        return applicationContext.getBean(DiseaseService.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiseaseEntity> getAll(UUID patientId) {
        logger.info("Call getAll method DiseaseService with patientId is {}", patientId);
        PatientEntity patient = patientService.findByIdOrThrow(patientId);
        return patient.getDiseaseEntities();
    }

    @Override
    @Transactional
    public DiseaseEntity create(UUID patientId, DiseaseEntity disease) {
        logger.info("Cal create method DiseaseService with patientId is {}", patientId);

        PatientEntity patient = patientService.findByIdOrThrow(patientId);
        if (checkConflictsWithActiveDiseases(disease, patient)) {
            throw new ActiveDiseaseExistsException(patientId, disease.getMkb10Code());
        }

        patient.addDisease(disease);
        disease.setPatientEntity(patient);
        return diseaseRepository.save(disease);
    }

    @Override
    @Transactional
    public DiseaseEntity update(UUID patientId, DiseaseEntity newDisease) {
        logger.info("Cal update method DiseaseService with patientId is {}", patientId);

        PatientEntity patient = patientService.findByIdOrThrow(patientId);
        DiseaseEntity disease = getDiseaseService().findDiseaseByIdAndPatient(newDisease.getId(), patient);
        if (checkConflictsWithActiveDiseases(disease, patient)) {
            throw new ActiveDiseaseExistsException(patientId, disease.getMkb10Code());
        }
        BeanUtils.copyProperties(newDisease, disease, "id", "patient");
        return disease;
    }

    @Override
    @Transactional
    public void delete(UUID patientId, UUID diseaseId) {
        logger.info("Cal delete method DiseaseService with patientId is {}", patientId);

        PatientEntity patient = patientService.findByIdOrThrow(patientId);
        DiseaseEntity disease = getDiseaseService().findDiseaseByIdAndPatient(diseaseId, patient);
        diseaseRepository.delete(disease);
    }

    public Optional<DiseaseEntity> activeDisease(PatientEntity patient, DiseaseEntity disease) {
        return diseaseRepository.findDiseaseEntityByPatientEntityAndMkb10CodeAndEndDateOfDiseaseIsNull(patient, disease.getMkb10Code());
    }

    public boolean checkConflictsWithActiveDiseases(DiseaseEntity disease, PatientEntity patient) {
        Optional<DiseaseEntity> activeDisease = activeDisease(patient, disease);
        return activeDisease.filter(diseaseEntity -> disease.getEndDateOfDisease() == null || !disease.getEndDateOfDisease().isBefore(diseaseEntity.getStartDateOfDisease())).isPresent();
    }

    @Transactional(readOnly = true)
    public DiseaseEntity findDiseaseByIdAndPatient(UUID diseaseId, PatientEntity patient) {
        return diseaseRepository.findDiseaseEntityByIdAndPatientEntity(diseaseId, patient).orElseThrow(() -> new NotFoundDiseaseException(patient.getId(), diseaseId));
    }

}
