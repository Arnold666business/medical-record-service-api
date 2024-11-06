package com.example.medicalrecordservice.exception.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
@Schema(description = "An error model containing a message about the need and possibility of additional errors")
public class ResponseError {
    @Schema(description = "Indicates the specific cause of the error",
            example = "Missing following statement: patient_id, type is UUID")
    protected String message;

    @Schema(description = "Describes additional information about what happened, may be null",
            example = "Required request parameter id for method parameter type is present but converted to null")
    protected List<String> errors;
}
