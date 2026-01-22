package com.medicinal.plant.controller;

import com.medicinal.plant.model.dto.response.ApiResponse;
import com.medicinal.plant.service.PlantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Admin Controller
 */
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Admin", description = "Administrative endpoints")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final PlantService plantService;

    @GetMapping("/dashboard")
    @Operation(summary = "Get admin dashboard data")
    public ResponseEntity<ApiResponse<String>> getDashboard() {
        return ResponseEntity.ok(ApiResponse.success("Admin dashboard data"));
    }

    @DeleteMapping("/plants/{id}")
    @Operation(summary = "Delete a plant")
    public ResponseEntity<ApiResponse<String>> deletePlant(@PathVariable Long id) {
        plantService.deletePlant(id);
        return ResponseEntity.ok(ApiResponse.success("Plant deleted successfully"));
    }

    @GetMapping("/logs")
    @Operation(summary = "Get system logs")
    public ResponseEntity<ApiResponse<String>> getSystemLogs() {
        return ResponseEntity.ok(ApiResponse.success("System logs"));
    }
}
