package com.example.medicalrecordservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Mkb10DiseaseInfoDto {
    private String code;
    private String diseaseName;
}
