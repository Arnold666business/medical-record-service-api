package com.example.medicalrecordservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PatientResponseDto {
    @JsonProperty(value = "patient_id")
    private UUID id;

    @JsonProperty(value = "last_name")
    private String lastName;

    @JsonProperty(value = "first_name")
    private String firstName;

    @JsonProperty(value = "middle_name")
    private String middleName;

    @JsonProperty(value = "gender")
    private String gender;

    @JsonProperty(value = "birth_date")
    private LocalDate birthDate;

    @JsonProperty(value = "oms_number")
    private String omsNumber;
}
