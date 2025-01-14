package com.example.medicalrecordservice.controller.view.request;

import com.example.medicalrecordservice.validation.constants.ExceptionValidationMessages;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class PatientProfile extends PatientPersonalInfo {
    @JsonProperty("patient_id")
    @NotNull(message = ExceptionValidationMessages.NOT_NULL)
    private UUID patientId;

}
