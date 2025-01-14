package com.example.medicalrecordservice.validation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@Schema
public class ValidationErrorResponse {
    private final List<Violation> violations;

}
