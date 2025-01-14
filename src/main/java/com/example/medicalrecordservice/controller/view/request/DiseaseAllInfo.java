package com.example.medicalrecordservice.controller.view.request;

import com.example.medicalrecordservice.validation.constants.ExceptionValidationMessages;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Setter
@Getter
@Schema
public class DiseaseAllInfo extends DiseaseGeneralInfo {
    @NotNull(message = ExceptionValidationMessages.NOT_NULL)
    @JsonProperty("disease_id")
    @Schema()
    private UUID diseaseId;

}
