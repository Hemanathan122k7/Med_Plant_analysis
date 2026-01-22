package com.medicinal.plant;

import com.medicinal.plant.controller.PlantController;
import com.medicinal.plant.model.dto.response.PlantResponse;
import com.medicinal.plant.service.PlantService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Plant Controller Integration Test
 */
@WebMvcTest(PlantController.class)
class PlantControllerTest {

    private static final String ALOE_VERA = "Aloe Vera";
    private static final String ALOE_VERA_SCIENTIFIC = "Aloe barbadensis miller";

    private final MockMvc mockMvc;

    @MockBean
    private PlantService plantService;

    PlantControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void testGetAllPlants() throws Exception {
        // Given
        List<PlantResponse> plants = Arrays.asList(
                PlantResponse.builder()
                        .id(1L)
                        .name(ALOE_VERA)
                        .scientificName(ALOE_VERA_SCIENTIFIC)
                        .build(),
                PlantResponse.builder()
                        .id(2L)
                        .name("Turmeric")
                        .scientificName("Curcuma longa")
                        .build()
        );

        when(plantService.getAllPlants()).thenReturn(plants);

        // When & Then
        mockMvc.perform(get("/api/plants/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].name").value(ALOE_VERA))
                .andExpect(jsonPath("$.data[1].name").value("Turmeric"));
    }

    @Test
    void testGetPlantById() throws Exception {
        // Given
        PlantResponse plant = PlantResponse.builder()
                .id(1L)
                .name(ALOE_VERA)
                .scientificName(ALOE_VERA_SCIENTIFIC)
                .description("A succulent plant with medicinal properties")
                .build();

        when(plantService.getPlantById(1L)).thenReturn(plant);

        // When & Then
        mockMvc.perform(get("/api/plants/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value(ALOE_VERA));
    }
}
