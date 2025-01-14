package com.example.medicalrecordservice.exception.patient;

import lombok.Getter;

import java.util.UUID;

@Getter
public class NotFoundPatientException extends RuntimeException {
    private final UUID patientId;

    public NotFoundPatientException(UUID patientId) {
        this.patientId = patientId;
    }

}
