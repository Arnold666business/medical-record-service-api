package com.example.medicalrecordservice.exception.disease;

import lombok.Getter;

import java.util.UUID;

@Getter
public class NotFoundDiseaseException extends RuntimeException {
    private final UUID patientId;
    private final UUID diseaseId;

    public NotFoundDiseaseException(UUID patientId, UUID diseaseId){
        super();
        this.patientId = patientId;
        this.diseaseId = diseaseId;
    }
}
