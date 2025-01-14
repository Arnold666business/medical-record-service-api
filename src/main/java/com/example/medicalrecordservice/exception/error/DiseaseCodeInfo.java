package com.example.medicalrecordservice.exception.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@SuperBuilder
@Getter
@Schema
public class DiseaseCodeInfo extends ResponseError {
    @Schema(description = "patient id", example = "5b222500-c0fc-40ce-b24a-d35ae89deed7")
    private UUID patientId;

    @Schema(description = "mkb10 code for disease", example = "W18.9")
    private String mkb10Code;

}
