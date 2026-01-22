package com.medicinal.plant.controller;

import com.medicinal.plant.model.dto.request.PlantSearchRequest;
import com.medicinal.plant.model.dto.request.SymptomSearchRequest;
import com.medicinal.plant.model.dto.request.VisualSearchRequest;
import com.medicinal.plant.model.dto.response.ApiResponse;
import com.medicinal.plant.model.dto.response.SearchResultResponse;
import com.medicinal.plant.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Search Controller
 */
@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
@Tag(name = "Search", description = "Plant search endpoints")
@CrossOrigin(origins = "*")
public class SearchController {

    private final SearchService searchService;

    @PostMapping("/general")
    @Operation(summary = "General plant search")
    public ResponseEntity<ApiResponse<SearchResultResponse>> generalSearch(
            @Valid @RequestBody PlantSearchRequest request) {
        SearchResultResponse result = searchService.performGeneralSearch(request);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @PostMapping("/by-symptoms")
    @Operation(summary = "Search plants by symptoms")
    public ResponseEntity<ApiResponse<SearchResultResponse>> searchBySymptoms(
            @Valid @RequestBody SymptomSearchRequest request) {
        SearchResultResponse result = searchService.searchBySymptoms(request);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @PostMapping("/by-visual")
    @Operation(summary = "Search plants by visual features")
    public ResponseEntity<ApiResponse<SearchResultResponse>> searchByVisualFeatures(
            @Valid @RequestBody VisualSearchRequest request) {
        SearchResultResponse result = searchService.searchByVisualFeatures(request);
        return ResponseEntity.ok(ApiResponse.success(result));
    }
}
