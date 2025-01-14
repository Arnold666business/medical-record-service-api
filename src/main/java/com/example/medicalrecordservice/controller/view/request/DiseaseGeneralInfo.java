package com.example.medicalrecordservice.controller.view.request;

import com.example.medicalrecordservice.validation.constants.ExceptionValidationMessages;
import com.example.medicalrecordservice.validation.constants.RegexpPatterns;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
public class DiseaseGeneralInfo {
    @NotBlank(message = ExceptionValidationMessages.NOT_BLANK)
    @Pattern(regexp = RegexpPatterns.mkb10_policy, message = ExceptionValidationMessages.MKB10_POLICY)
    @JsonProperty(value = "mkb10_code")
    private String mkb10Code;

    @NotNull(message = ExceptionValidationMessages.NOT_NULL)
    @PastOrPresent(message = ExceptionValidationMessages.NOT_IN_FUTURE)
    @JsonProperty(value = "start_date_of_disease")
    private LocalDate startDateOfDisease;

    @PastOrPresent(message = ExceptionValidationMessages.NOT_IN_FUTURE)
    @JsonProperty(value = "end_date_of_disease")
    private LocalDate endDateOfDisease;

    @NotBlank(message = ExceptionValidationMessages.NOT_BLANK)
    @Pattern(regexp = RegexpPatterns.name_pattern, message = ExceptionValidationMessages.INCORRECT_FORMAT)
    @JsonProperty(value = "prescription")
    private String prescription;

}
