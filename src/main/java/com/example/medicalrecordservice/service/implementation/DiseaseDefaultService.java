package com.example.medicalrecordservice.service.implementation;

import com.example.medicalrecordservice.exception.disease.ActiveDiseaseExistsException;
import com.example.medicalrecordservice.helper.DiseaseHelper;
import com.example.medicalrecordservice.helper.PatientHelper;
import com.example.medicalrecordservice.model.DiseaseEntity;
import com.example.medicalrecordservice.model.PatientEntity;
import com.example.medicalrecordservice.repository.DiseaseRepository;
import com.example.medicalrecordservice.service.DiseaseService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DiseaseDefaultService implements DiseaseService {
    private final DiseaseRepository diseaseRepository;

    private final PatientHelper patientHelper;

    private final DiseaseHelper diseaseHelper;

    private final static Logger logger = LoggerFactory.getLogger(DiseaseDefaultService.class);

    @Override
    @Transactional(readOnly = true)
    public List<DiseaseEntity> getAll(UUID patientId) {
        logger.info("Call getAll method DiseaseService with patientId is {}", patientId);
        PatientEntity patient = patientHelper.findByIdOrThrow(patientId);
        return patient.getDiseaseEntities();
    }

    @Override
    @Transactional
    public DiseaseEntity create(UUID patientId, DiseaseEntity disease) {
        logger.info("Cal create method DiseaseService with patientId is {}", patientId);

        PatientEntity patient = patientHelper.findByIdOrThrow(patientId);
        if(checkConflictsWithActiveDiseases(disease, patient)){
            throw new ActiveDiseaseExistsException(patientId, disease.getMkb10Code());
        }

        patient.addDisease(disease);
        disease.setPatientEntity(patient);
        return diseaseRepository.save(disease);
    }

    @Override
    public DiseaseEntity update(UUID patientId, DiseaseEntity newDisease) {
        logger.info("Cal update method DiseaseService with patientId is {}", patientId);

        PatientEntity patient = patientHelper.findByIdOrThrow(patientId);
        DiseaseEntity disease = diseaseHelper.findDiseaseByIdAndPatient(newDisease.getId(), patient);
        if(checkConflictsWithActiveDiseases(disease, patient)){
            throw new ActiveDiseaseExistsException(patientId, disease.getMkb10Code());
        }

        if(disease.equalsByFields(newDisease) ){
            return disease;
        }

        newDisease.setPatientEntity(patient);
        return diseaseRepository.save(newDisease);
    }

    @Override
    @Transactional
    public void delete(UUID patientId, UUID diseaseId) {
        logger.info("Cal delete method DiseaseService with patientId is {}", patientId);

        PatientEntity patient = patientHelper.findByIdOrThrow(patientId);
        DiseaseEntity disease = diseaseHelper.findDiseaseByIdAndPatient(diseaseId, patient);
        diseaseRepository.delete(disease);
    }

    public Optional<DiseaseEntity> activeDisease(PatientEntity patient, DiseaseEntity disease){
        return diseaseRepository.findDiseaseEntityByPatientEntityAndMkb10CodeAndEndDateOfDiseaseIsNull(patient, disease.getMkb10Code());
    }

    public boolean checkConflictsWithActiveDiseases(DiseaseEntity disease, PatientEntity patient){
        Optional<DiseaseEntity> activeDisease = activeDisease(patient, disease);
        return activeDisease.filter(diseaseEntity -> disease.getEndDateOfDisease() == null ||
                !disease.getEndDateOfDisease().isBefore(diseaseEntity.getStartDateOfDisease())).isPresent();
    }
}
