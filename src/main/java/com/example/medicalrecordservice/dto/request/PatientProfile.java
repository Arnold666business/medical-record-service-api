package com.example.medicalrecordservice.dto.request;

import com.example.medicalrecordservice.validation.constants.ExceptionValidationMessages;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PatientProfile extends PatientPersonalInfo{
    @JsonProperty("patient_id")
    @NotNull(message = ExceptionValidationMessages.NOT_NULL)
    private UUID patientId;
}
