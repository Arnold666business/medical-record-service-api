package com.example.medicalrecordservice.dto.request;


import com.example.medicalrecordservice.validation.constants.ExceptionValidationMessages;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Setter
@Getter
@Schema
public class DiseaseAllInfo extends DiseaseGeneralInfo{
    @NotNull(message = ExceptionValidationMessages.NOT_NULL)
    @JsonProperty("disease_id")
    @Schema()
    private UUID diseaseId;
}
