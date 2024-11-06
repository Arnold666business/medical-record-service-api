package com.example.medicalrecordservice.mapper;

import com.example.medicalrecordservice.dto.response.Mkb10DiseaseInfoDto;
import com.example.medicalrecordservice.model.Mkb10DiseaseInfoEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface Mkb10Mapper {
    Mkb10DiseaseInfoDto toMkb10DiseaseInfoDto(Mkb10DiseaseInfoEntity mkb10DiseaseInfoEntity);

    List<Mkb10DiseaseInfoDto> toMkb10DiseaseInfoDtoList(List<Mkb10DiseaseInfoEntity> mkb10DiseaseInfoEntities);
}
