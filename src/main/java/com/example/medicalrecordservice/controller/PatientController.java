package com.example.medicalrecordservice.controller;

import com.example.medicalrecordservice.dto.request.PatientPersonalInfo;
import com.example.medicalrecordservice.dto.request.PatientProfile;
import com.example.medicalrecordservice.dto.response.PatientResponseDto;
import com.example.medicalrecordservice.exception.error.ResponseError;
import com.example.medicalrecordservice.exception.error.patient.PatientErrorDto;
import com.example.medicalrecordservice.mapper.PatientMapper;
import com.example.medicalrecordservice.model.PatientEntity;
import com.example.medicalrecordservice.service.PatientService;
import com.example.medicalrecordservice.swagger.StandardApiResponses;
import com.example.medicalrecordservice.validation.ValidationErrorResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@StandardApiResponses
@Tag(name = "Patient controller", description = "Patient data management")
public class PatientController {
    private static final String GET_POST_PUT_PATIENT_ENDPOINT = "/patient";
    private static final String DELETE_PATIENT_ENDPOINT = "/patient/{patient_id}";

    private final PatientService patientService;

    private final PatientMapper patientMapper;



    @Operation(summary = "Getting a list of patients" ,
            description = "Allows you to get a list of all patients, if there are no patients then an empty list is returned")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful return",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PatientResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Resource not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseError.class))),
    })
    @GetMapping(GET_POST_PUT_PATIENT_ENDPOINT)
    public ResponseEntity<List<PatientResponseDto>> getAllPatients(){
        List<PatientEntity> patientEntities = patientService.getAll();
        return ResponseEntity.ok(patientMapper.toResponseDtoList(patientEntities));
    }


    @Operation(summary = "Create a patient" ,
            description = "Allows adding new patients only with a unique compulsory medical insurance policy")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful create patient",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Malformed JSON request 100 #1 <br> "+
                    "Invalid parameter request type #2 ",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseError.class))),
            @ApiResponse(responseCode = "422", description = "Validation error when setting values",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Resource not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseError.class))),
            @ApiResponse(responseCode = "409", description = "Duplicate oms number error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PatientErrorDto.class)))
    })
    @PostMapping(value = GET_POST_PUT_PATIENT_ENDPOINT)
    public ResponseEntity<Void> createPatient(@RequestBody @Valid PatientPersonalInfo patientRequest){
        PatientEntity patient = patientService.create(patientMapper.patientPersonalInfoToEntity(patientRequest));
        return ResponseEntity.status(HttpStatus.CREATED).header("Location",
                String.format("/patient/%s", patient.getId())).build();
    }


    @Operation(summary = "Replace an existing patient" ,
            description = "Allows you to replace an entity with the declared one if they have equal IDs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful return",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PatientResponseDto.class))),

            @ApiResponse(responseCode = "422", description = "Validation error when setting values",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorResponse.class))),
            @ApiResponse(responseCode = "404", description =
                    "Resource not found 404 #1 <br/>" +
                            "Not found patient 404 #2 <br/>",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseError.class))),
            @ApiResponse(responseCode = "400", description = "Malformed JSON request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseError.class))),
            @ApiResponse(responseCode = "409", description = "Duplicate oms number error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PatientErrorDto.class)))
    })
    @PutMapping(GET_POST_PUT_PATIENT_ENDPOINT)
    public ResponseEntity<PatientResponseDto> updatePatient(@RequestBody @Valid PatientProfile patientRequest){
        PatientEntity patient = patientService.update(patientMapper.patientProfileToEntity(patientRequest));
        return ResponseEntity.ok(patientMapper.toResponseDto(patient));
    }


    @Operation(summary = "Delete patient" ,
            description = "Allows you to delete a patient with the specified ID if it exists")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful delete",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PatientResponseDto.class))),
            @ApiResponse(responseCode = "400", description =
                    "Occurs when the type of the passed request parameter is missing 400 #1 <br/>" +
                            "Invalid parameter request type 400 #2",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseError.class))),
            @ApiResponse(responseCode = "404", description =
                    "Not find patient 404 #1 <br/>" +
                            "Resource not found 404 #2",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseError.class)))
    })
    @DeleteMapping(DELETE_PATIENT_ENDPOINT)
    public ResponseEntity<Void> deletePatient(@PathVariable("patient_id") @Parameter(description = "Patient id",
            example = "0c565284-3676-4ca6-bcd0-b77de640872d") UUID patientId){
        patientService.delete(patientId);
        return ResponseEntity.ok().build();
    }
}
