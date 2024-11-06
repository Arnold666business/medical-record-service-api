package com.example.medicalrecordservice.mapper;

import com.example.medicalrecordservice.dto.request.DiseaseAllInfo;
import com.example.medicalrecordservice.dto.request.DiseaseGeneralInfo;
import com.example.medicalrecordservice.dto.response.DiseaseResponseDto;
import com.example.medicalrecordservice.model.DiseaseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DiseaseMapper {
    @Mapping(target = "diseaseId", source = "id")
    DiseaseAllInfo fromEntityToDiseaseAllInfo(DiseaseEntity diseaseEntity);

    List<DiseaseAllInfo> fromEntityListToDiseaseAllInfoList(List<DiseaseEntity> diseaseEntities);

    DiseaseEntity fromDiseaseGeneralInfoToEntity(DiseaseGeneralInfo diseaseGeneralInfo);

    @Mapping(source = "diseaseId", target = "id")
    DiseaseEntity fromDiseaseAllInfoToDto(DiseaseAllInfo diseaseAllInfo);

    @Mapping(target = "diseaseId", source = "id")
    DiseaseResponseDto fromDiseaseEntityToResponseDto(DiseaseEntity diseaseEntity);
}
