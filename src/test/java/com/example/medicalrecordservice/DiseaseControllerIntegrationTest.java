package com.example.medicalrecordservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DiseaseControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllDisease() throws Exception {
        UUID patientId = UUID.randomUUID();

        mockMvc.perform(get("/patient/" + patientId + "/disease")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testCreateDisease() throws Exception {
        UUID patientId = UUID.randomUUID();
        String requestBody = """
                {
                    "mkb10_code": "A01",
                    "start_date_of_disease": "2023-01-01",
                    "endDate": "2023-12-31",
                    "prescription": "Updated Disease Description"
                }
                """;

        mockMvc.perform(post("/patient/" + patientId + "/disease")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/patient/" + patientId + "/disease/")));
    }

    @Test
    public void testUpdateDisease() throws Exception {
        UUID patientId = UUID.randomUUID();
        String requestBody = """
                {
                    "disease_id": "0c565284-3676-4ca6-bcd0-b77de640872d"
                    "mkb10_code": "A01",
                    "start_date_of_disease": "2023-01-01",
                    "end_date_of_disease": "2023-12-31",
                    "prescription": "Updated Disease Description"
                }
                """;

        mockMvc.perform(put("/patient/" + patientId + "/disease")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.diseaseCode").value("A01"))
                .andExpect(jsonPath("$.description").value("Updated Disease Description"));
    }

    @Test
    public void testDeleteDisease() throws Exception {
        UUID patientId = UUID.randomUUID();
        UUID diseaseId = UUID.randomUUID();

        mockMvc.perform(delete("/patient/" + patientId + "/disease/" + diseaseId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
