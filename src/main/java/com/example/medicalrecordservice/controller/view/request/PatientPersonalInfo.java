package com.example.medicalrecordservice.controller.view.request;

import com.example.medicalrecordservice.validation.constants.ExceptionValidationMessages;
import com.example.medicalrecordservice.validation.constants.LengthConstraints;
import com.example.medicalrecordservice.validation.constants.RegexpPatterns;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
public class PatientPersonalInfo {
    @NotBlank(message = ExceptionValidationMessages.NOT_BLANK)
    @Size(max = LengthConstraints.MAX_LAST_NAME_LENGTH, min = LengthConstraints.MIN_LAST_NAME_LENGTH, message = ExceptionValidationMessages.INCORRECT_SIZE)
    @Pattern(regexp = RegexpPatterns.name_pattern, message = ExceptionValidationMessages.INCORRECT_FORMAT)
    @JsonProperty(value = "last_name")
    private String lastName;

    @NotBlank(message = ExceptionValidationMessages.NOT_BLANK)
    @Size(max = LengthConstraints.MAX_FIRST_NAME_LENGTH, min = LengthConstraints.MIN_FIRST_NAME_LENGTH, message = ExceptionValidationMessages.INCORRECT_SIZE)
    @Pattern(regexp = RegexpPatterns.name_pattern, message = ExceptionValidationMessages.INCORRECT_FORMAT)
    @JsonProperty(value = "first_name")
    private String firstName;

    @Size(max = LengthConstraints.MAX_MIDDLE_NAME_LENGTH, message = ExceptionValidationMessages.INCORRECT_SIZE)
    @Pattern(regexp = RegexpPatterns.name_pattern, message = ExceptionValidationMessages.INCORRECT_FORMAT)
    @JsonProperty(value = "middle_name")
    private String middleName;

    @NotBlank(message = ExceptionValidationMessages.NOT_BLANK)
    @Size(max = LengthConstraints.MAX_GENDER_LENGTH, min = LengthConstraints.MIN_GENDER_LENGTH, message = ExceptionValidationMessages.INCORRECT_SIZE)
    @Pattern(regexp = RegexpPatterns.name_pattern, message = ExceptionValidationMessages.INCORRECT_FORMAT)
    @JsonProperty(value = "gender")
    private String gender;

    @NotNull(message = ExceptionValidationMessages.NOT_BLANK)
    @PastOrPresent(message = ExceptionValidationMessages.NOT_IN_FUTURE)
    @JsonProperty(value = "birth_date")
    private LocalDate birthDate;

    @NotBlank(message = ExceptionValidationMessages.NOT_BLANK)
    @Size(max = LengthConstraints.OMS_NUMBER_FIXED_LENGTH, min = LengthConstraints.OMS_NUMBER_FIXED_LENGTH, message = ExceptionValidationMessages.INCORRECT_SIZE)
    @Pattern(regexp = RegexpPatterns.omc_policy_pattern, message = ExceptionValidationMessages.OMC_POLICY)
    @JsonProperty(value = "oms_number")
    private String omsNumber;

}
