package com.example.medicalrecordservice.validation;

import com.example.medicalrecordservice.utility.common.SneakCaseSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Violation {
    @JsonSerialize(using = SneakCaseSerializer.class)
    private final String fieldName;

    @JsonSerialize(using = SneakCaseSerializer.class)
    private final String message;

}
