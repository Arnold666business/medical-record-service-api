package com.example.medicalrecordservice.exception.error.patient;

import com.example.medicalrecordservice.exception.error.ResponseError;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@SuperBuilder
@Getter
@Schema
public class PatientErrorDto extends ResponseError {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "patient_id")
    @Schema(description = "patient id", example = "5b222500-c0fc-40ce-b24a-d35ae89deed7")
    private UUID id;

    @JsonProperty(value = "oms_police")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Schema(description = "patient oms police, 16 numbers", example = "1234567898765432")
    private String oms;

}
