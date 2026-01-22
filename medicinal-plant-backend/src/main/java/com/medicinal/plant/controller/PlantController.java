package com.medicinal.plant.controller;

import com.medicinal.plant.model.dto.response.ApiResponse;
import com.medicinal.plant.model.dto.response.PlantResponse;
import com.medicinal.plant.service.PlantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Plant Controller - Main API endpoints for plant operations
 */
@RestController
@RequestMapping("/api/plants")
@RequiredArgsConstructor
@Tag(name = "Plants", description = "Plant management endpoints")
@CrossOrigin(origins = "*")
public class PlantController {

    private final PlantService plantService;

    @GetMapping("/all")
    @Operation(summary = "Get all plants", description = "Retrieve a list of all medicinal plants")
    public ResponseEntity<ApiResponse<List<PlantResponse>>> getAllPlants() {
        List<PlantResponse> plants = plantService.getAllPlants();
        return ResponseEntity.ok(ApiResponse.success("Plants retrieved successfully", plants));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get plant by ID", description = "Retrieve detailed information about a specific plant")
    public ResponseEntity<ApiResponse<PlantResponse>> getPlantById(@PathVariable Long id) {
        PlantResponse plant = plantService.getPlantById(id);
        return ResponseEntity.ok(ApiResponse.success(plant));
    }

    @GetMapping("/search")
    @Operation(summary = "Search plants by name", description = "Search for plants by name or scientific name")
    public ResponseEntity<ApiResponse<List<PlantResponse>>> searchPlants(@RequestParam String query) {
        List<PlantResponse> plants = plantService.searchPlants(query);
        return ResponseEntity.ok(ApiResponse.success("Search completed", plants));
    }

    @GetMapping("/top-rated")
    @Operation(summary = "Get top rated plants", description = "Retrieve plants with highest ratings")
    public ResponseEntity<ApiResponse<List<PlantResponse>>> getTopRatedPlants() {
        List<PlantResponse> plants = plantService.getTopRatedPlants();
        return ResponseEntity.ok(ApiResponse.success(plants));
    }

    @GetMapping("/by-type")
    @Operation(summary = "Get plants by type", description = "Filter plants by their type (herb, shrub, tree, etc.)")
    public ResponseEntity<ApiResponse<List<PlantResponse>>> getPlantsByType(@RequestParam String type) {
        List<PlantResponse> plants = plantService.getPlantsByType(type);
        return ResponseEntity.ok(ApiResponse.success(plants));
    }
}
