package com.example.medicalrecordservice.exception.disease;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ActiveDiseaseExistsException extends RuntimeException {
    private final UUID patientId;
    private final String mkb10Code;

    public ActiveDiseaseExistsException(UUID patientId, String mkb10Code){
        this.patientId = patientId;
        this.mkb10Code = mkb10Code;
    }
}
