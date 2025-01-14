package com.example.medicalrecordservice.controller.view.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
public class DiseaseResponseDto {
    @JsonProperty("disease_id")
    private UUID diseaseId;

    @JsonProperty(value = "mkb10_code")
    private String mkb10Code;

    @JsonProperty(value = "start_date_of_disease")
    private LocalDate startDateOfDisease;

    @JsonProperty(value = "end_date_of_disease")
    private LocalDate endDateOfDisease;

    @JsonProperty(value = "prescription")
    private String prescription;

}
