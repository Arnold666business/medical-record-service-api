package com.example.medicalrecordservice;

import com.example.medicalrecordservice.dto.request.PatientPersonalInfo;
import com.example.medicalrecordservice.dto.request.PatientProfile;
import com.example.medicalrecordservice.dto.response.PatientResponseDto;
import com.example.medicalrecordservice.mapper.PatientMapper;
import com.example.medicalrecordservice.model.PatientEntity;
import com.example.medicalrecordservice.service.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PatientControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper  objectMapper;

    @MockBean
    PatientService patientService;

    @MockBean
    private PatientMapper patientMapper;

    private PatientEntity patientEntity;
    private PatientResponseDto patientResponseDto;
    private UUID patientId;




    @BeforeEach
    void setProperty(){
        patientId = UUID.randomUUID();
    }

    @Test
    void getAllPatients_shouldReturnListOfPatients() throws Exception, NullPointerException {
        when(patientService.getAll()).thenReturn(List.of(patientEntity));
        when(patientMapper.toResponseDtoList(List.of(patientEntity))).thenReturn(List.of(patientResponseDto));

        mockMvc.perform(get("/patient")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testCreatePatient_Success() throws Exception {
        PatientPersonalInfo patientRequest = new PatientPersonalInfo();
        patientRequest.setOmsNumber("1234567890");

        ResultActions response = mockMvc.perform(post("/patient")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patientRequest)));

        response.andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }
    @Test
    void testUpdatePatient_Success() throws Exception {
        PatientProfile patientRequest = new PatientProfile();
        patientRequest.setPatientId(UUID.randomUUID());
        patientRequest.setOmsNumber("9876543210");

        PatientEntity updatedPatient = new PatientEntity();
        updatedPatient.setId(patientRequest.getPatientId());
        updatedPatient.setOmsNumber(patientRequest.getOmsNumber());
        when(patientService.update(any(PatientEntity.class))).thenReturn(updatedPatient);
        when(patientMapper.toResponseDto(any(PatientEntity.class))).thenReturn(new PatientResponseDto());

        mockMvc.perform(put("/patient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testDeletePatient_Success() throws Exception {
        UUID patientId = UUID.randomUUID();

        doNothing().when(patientService).delete(patientId);

        mockMvc.perform(delete("patient/" + patientId))
                .andExpect(status().isOk());
    }

}
