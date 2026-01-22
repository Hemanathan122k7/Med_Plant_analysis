package com.medicinal.plant.controller;

import com.medicinal.plant.model.dto.response.ApiResponse;
import com.medicinal.plant.service.SymptomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Symptom Controller
 */
@RestController
@RequestMapping("/api/symptoms")
@RequiredArgsConstructor
@Tag(name = "Symptoms", description = "Symptom management endpoints")
@CrossOrigin(origins = "*")
public class SymptomController {

    private final SymptomService symptomService;

    @GetMapping("/all")
    @Operation(summary = "Get all symptoms")
    public ResponseEntity<ApiResponse<List<String>>> getAllSymptoms() {
        List<String> symptoms = symptomService.getAllSymptoms();
        return ResponseEntity.ok(ApiResponse.success(symptoms));
    }

    @GetMapping("/categories")
    @Operation(summary = "Get symptom categories")
    public ResponseEntity<ApiResponse<List<String>>> getSymptomCategories() {
        List<String> categories = symptomService.getSymptomCategories();
        return ResponseEntity.ok(ApiResponse.success(categories));
    }
}
