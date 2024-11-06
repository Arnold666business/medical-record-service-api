package com.example.medicalrecordservice.exception.error.disease;

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
public class DiseaseErrorInfo extends ResponseError {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "disease_id")
    @Schema(description = "disease id",
            example = "5b222500-c0fc-40ce-b24a-d35ae89deed7")
    private UUID id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "mkb10_code")
    @Schema(description = "mkb10 Code",
            example = "Z42.3")
    private String mkb10Code;
}
