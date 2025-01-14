package com.example.medicalrecordservice.exception.patient;

import lombok.Getter;

@Getter
public class DuplicateOmsPolicyException extends RuntimeException {
    private final String omsNumber;

    public DuplicateOmsPolicyException(String omsNumber) {
        this.omsNumber = omsNumber;
    }

}
