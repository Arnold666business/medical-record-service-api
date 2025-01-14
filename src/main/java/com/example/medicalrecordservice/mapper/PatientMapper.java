package com.example.medicalrecordservice.mapper;

import com.example.medicalrecordservice.controller.view.request.PatientPersonalInfo;
import com.example.medicalrecordservice.controller.view.request.PatientProfile;
import com.example.medicalrecordservice.controller.view.response.PatientResponseDto;
import com.example.medicalrecordservice.model.PatientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface PatientMapper {
    PatientResponseDto toResponseDto(PatientEntity patientEntity);

    List<PatientResponseDto> toResponseDtoList(List<PatientEntity> patientEntities);

    PatientEntity patientPersonalInfoToEntity(PatientPersonalInfo patientPersonalInfo);

    @Mapping(target = "id", source = "patientId")
    PatientEntity patientProfileToEntity(PatientProfile patientProfile);

}
