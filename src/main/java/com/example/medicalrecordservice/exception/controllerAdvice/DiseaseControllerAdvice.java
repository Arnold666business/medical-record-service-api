package com.example.medicalrecordservice.exception.controllerAdvice;

import com.example.medicalrecordservice.exception.disease.ActiveDiseaseExistsException;
import com.example.medicalrecordservice.exception.disease.DataValidateException;
import com.example.medicalrecordservice.exception.disease.NotFoundDiseaseException;
import com.example.medicalrecordservice.exception.error.DiseaseCodeInfo;
import com.example.medicalrecordservice.exception.error.disease.DiseaseErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.UUID;

@RestControllerAdvice
public class DiseaseControllerAdvice {
    @ExceptionHandler(ActiveDiseaseExistsException.class)
    public ResponseEntity<DiseaseCodeInfo> diseaseRecurrenceHandler(ActiveDiseaseExistsException exception) {
        String mkb10Code = exception.getMkb10Code();
        UUID patientId = exception.getPatientId();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(DiseaseCodeInfo.builder().message(String.format("Patient with ID %s already has a disease with code %s", patientId, mkb10Code)).mkb10Code(mkb10Code).patientId(patientId).build());
    }

    @ExceptionHandler(NotFoundDiseaseException.class)
    public ResponseEntity<DiseaseErrorInfo> notFoundDiseaseHandler(NotFoundDiseaseException exception) {
        UUID diseaseId = exception.getDiseaseId();
        UUID patientId = exception.getPatientId();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(DiseaseErrorInfo.builder().id(diseaseId).message(String.format("Patient with ID %s, diseases with ID %s not found", patientId, diseaseId)).build());
    }

    @ExceptionHandler(DataValidateException.class)
    public ResponseEntity<DiseaseErrorInfo> notFoundDiseaseHandler(DataValidateException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(DiseaseErrorInfo.builder().message(exception.getMessage()).build());
    }

}
