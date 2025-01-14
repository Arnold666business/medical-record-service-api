package com.example.medicalrecordservice.controller;

import com.example.medicalrecordservice.controller.view.request.DiseaseAllInfo;
import com.example.medicalrecordservice.controller.view.request.DiseaseGeneralInfo;
import com.example.medicalrecordservice.controller.view.response.DiseaseResponseDto;
import com.example.medicalrecordservice.exception.error.DiseaseCodeInfo;
import com.example.medicalrecordservice.exception.error.ResponseError;
import com.example.medicalrecordservice.mapper.DiseaseMapper;
import com.example.medicalrecordservice.model.DiseaseEntity;
import com.example.medicalrecordservice.service.DiseaseService;
import com.example.medicalrecordservice.swagger.StandardApiResponses;
import com.example.medicalrecordservice.validation.ValidationErrorResponse;
import com.example.medicalrecordservice.validation.customValidate.DateValidator;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "Disease controller", description = "Manages patients diseases")
@StandardApiResponses
public class DiseaseController {
    private static final String GET_POST_PUT_DISEASE_ENDPOINT = "/patient/{patient_id}/disease";

    private static final String DELETE_DISEASE_ENDPOINT = "/patient/{patient_id}/disease/{disease_id}";

    private final DiseaseService diseaseService;

    private final DiseaseMapper diseaseMapper;

    @Operation(summary = "Getting a list of diseases",
            description = "Allows you to get a complete list of diseases of the specified user, in case of diseases returns an empty list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful return",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DiseaseAllInfo.class))),
            @ApiResponse(responseCode = "404", description =
                    "Not found patient 404 #1 <br/>" +
                            "Resource not found 404 #2",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseError.class))),
            @ApiResponse(responseCode = "400", description =
                    "Occurs when the type of the passed request parameter is missing 400 #1 <br/>" +
                            "Invalid parameter request type 400 #2",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseError.class)))
    })
    @GetMapping(GET_POST_PUT_DISEASE_ENDPOINT)
    @ResponseStatus(HttpStatus.OK)
    public List<DiseaseAllInfo> getAllDisease(@PathVariable("patient_id")
                                              @Parameter(description = "Patient id",
                                                      example = "0c565284-3676-4ca6-bcd0-b77de640872d") UUID patientId) {
        List<DiseaseEntity> diseaseEntities = diseaseService.getAll(patientId);
        return diseaseMapper.fromEntityListToDiseaseAllInfoList(diseaseEntities);
    }

    @Operation(summary = "Creating a disease",
            description = "Allows you to add a disease for a patient with the specified ID.Diseases " +
                    "are compared by their mkb10 code. You cannot add a disease if it already exists " +
                    "in this patient without specifying the date of recovery. At the same time, if " +
                    "the disease being added is already present in the patient, but at the same time " +
                    "he has a recovery date that is earlier than the start date of an already existing " +
                    "incomplete disease, then you can add it. Control over other conflicts related to the " +
                    "same diseases remains with the client using the api, or with the administrators of the system used.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful created"),
            @ApiResponse(responseCode = "422", description = "Validation error when setting values",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorResponse.class))),
            @ApiResponse(responseCode = "400", description =
                    "Malformed JSON request 400 #1 <br/>" +
                            "Occurs when the type of the passed request parameter is missing 400 #2 <br/>" +
                            "Invalid parameter request type 400 #3",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseError.class))),
            @ApiResponse(responseCode = "404", description =
                    "Resource not found 404 #1 <br/>" +
                            "Not found patient 404 br/> #2",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseError.class))),
            @ApiResponse(responseCode = "409", description = "When trying to add a new disease, the patient finds out that he still has it.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DiseaseCodeInfo.class)))
    })
    @PostMapping(GET_POST_PUT_DISEASE_ENDPOINT)
    @ResponseStatus(HttpStatus.CREATED)
    public DiseaseResponseDto createDisease(@PathVariable("patient_id")
                                            @Parameter(description = "Patient id",
                                                    example = "0c565284-3676-4ca6-bcd0-b77de640872d") UUID patientId,
                                            @RequestBody @Valid @DateValidator DiseaseGeneralInfo diseaseInfo) {
        DiseaseEntity disease = diseaseService
                .create(patientId, diseaseMapper.fromDiseaseGeneralInfoToEntity(diseaseInfo));
        return diseaseMapper.fromDiseaseEntityToResponseDto(disease);
    }

    @Operation(summary = "Update a disease", description = "Allows you to update information about a specific user's specified disease")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful update disease",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DiseaseResponseDto.class))),
            @ApiResponse(responseCode = "400", description =
                    "Malformed JSON request 400 #1 <br/>" +
                            "Occurs when the type of the passed request parameter is missing 400 #2 <br/>" +
                            "Invalid parameter request type 400 #3",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseError.class))),
            @ApiResponse(responseCode = "422", description = "Validation error when setting values",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ValidationErrorResponse.class))),
            @ApiResponse(responseCode = "404", description =
                    "Resource not found 404 #1 <br/>" +
                            "Not found patient 404 br/> #2" +
                            "Not found disease 404 #3",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseError.class))),
            @ApiResponse(responseCode = "409", description = "When trying to add a new disease, the patient finds out that he still has it.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DiseaseCodeInfo.class)))
    })
    @PutMapping(GET_POST_PUT_DISEASE_ENDPOINT)
    @ResponseStatus(HttpStatus.OK)
    public DiseaseResponseDto updateDisease(@PathVariable("patient_id")
                                            @Parameter(description = "Patient id",
                                                    example = "0c565284-3676-4ca6-bcd0-b77de640872d") UUID patientId,
                                            @RequestBody @Valid DiseaseAllInfo diseaseInfo) {
        DiseaseEntity disease = diseaseService.update(patientId, diseaseMapper.fromDiseaseAllInfoToDto(diseaseInfo));
        return diseaseMapper.fromDiseaseEntityToResponseDto(disease);
    }

    @Operation(summary = "Update a disease", description = "Allows you to update information about a specific user's specified disease")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful delete disease"),
            @ApiResponse(responseCode = "400", description =
                    "Occurs when the type of the passed request parameter is missing 400 #1 <br/>" +
                            "Invalid parameter request type 400 #2",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseError.class))),

            @ApiResponse(responseCode = "404", description =
                    "Not found patient 404 #1 <br/>" +
                            "Not found disease 404 #2 <br/>" +
                            "Resource not found 404 #3",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseError.class)))
    })
    @DeleteMapping(DELETE_DISEASE_ENDPOINT)
    @ResponseStatus(HttpStatus.OK)
    public void deleteDisease(@PathVariable("patient_id")
                              @Parameter(description = "Patient id",
                                      example = "0c565284-3676-4ca6-bcd0-b77de640872d") UUID patientID,
                              @PathVariable("disease_id") @Parameter(description = "Disease id",
                                      example = "0c565284-3676-4ca6-bcd0-b77de640872d") UUID diseaseId) {
        diseaseService.delete(patientID, diseaseId);
    }

}
