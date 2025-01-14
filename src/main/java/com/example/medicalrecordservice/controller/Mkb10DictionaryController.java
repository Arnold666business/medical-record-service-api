package com.example.medicalrecordservice.controller;

import com.example.medicalrecordservice.controller.view.response.Mkb10DiseaseInfoDto;
import com.example.medicalrecordservice.mapper.Mkb10Mapper;
import com.example.medicalrecordservice.service.Mkb10DictionaryService;
import com.example.medicalrecordservice.swagger.StandardApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Mkb10 dictionary controller", description = "Managing data from the mkb10 dictionary")
@StandardApiResponses
public class Mkb10DictionaryController {
    private static final String GET_DICTIONARY_ENDPOINT = "/dictionary/mkb10";

    private final Mkb10DictionaryService mkb10DictionaryService;

    private final Mkb10Mapper mkb10Mapper;

    @Operation(summary = "Get all mkb10 information dtos", description = "Allows you to get all the " +
            "values of the Mkb10 entity, which contains information about a specific disease, " +
            "provided with a code, the code is the primary key of the table")
    @GetMapping(GET_DICTIONARY_ENDPOINT)
    @ResponseStatus(HttpStatus.OK)
    public List<Mkb10DiseaseInfoDto> getAllMkb10Info() {
        return mkb10Mapper.toMkb10DiseaseInfoDtoList(mkb10DictionaryService.getAll());
    }

}
