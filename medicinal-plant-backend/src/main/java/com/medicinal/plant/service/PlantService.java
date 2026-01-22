package com.medicinal.plant.service;

import com.medicinal.plant.model.dto.response.PlantResponse;

import java.util.List;

/**
 * Plant Service Interface
 */
public interface PlantService {

    List<PlantResponse> getAllPlants();

    PlantResponse getPlantById(Long id);

    List<PlantResponse> searchPlants(String query);

    List<PlantResponse> getTopRatedPlants();

    List<PlantResponse> getPlantsByType(String type);

    void deletePlant(Long id);
}
