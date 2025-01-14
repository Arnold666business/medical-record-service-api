package com.example.medicalrecordservice.exception.controllerAdvice;

import com.example.medicalrecordservice.exception.error.patient.PatientErrorDto;
import com.example.medicalrecordservice.exception.patient.DuplicateOmsPolicyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PatientControllerAdvice {
    @ExceptionHandler(DuplicateOmsPolicyException.class)
    public ResponseEntity<PatientErrorDto> duplicateOmsPolicyExceptionHandler(DuplicateOmsPolicyException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(PatientErrorDto.builder().oms(exception.getOmsNumber()).message("The number of the compulsory medical insurance policy must not be repeated.").build());
    }

}
