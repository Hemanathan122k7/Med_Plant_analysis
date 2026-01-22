package com.medicinal.plant.controller;

import com.medicinal.plant.dto.PlantIdentificationResponse;
import com.medicinal.plant.service.PlantNetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/identify")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Plant Identification", description = "Plant identification using image upload")
@CrossOrigin(origins = "*")
public class PlantIdentificationController {

    private final PlantNetService plantNetService;

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        log.info("🧪 Test endpoint called");
        return ResponseEntity.ok("PlantNet API endpoint is working!");
    }

    @PostMapping("/image")
    @Operation(summary = "Identify plant from image", description = "Upload an image to identify the plant and get medicinal information")
    public ResponseEntity<?> identifyPlantFromImage(@RequestParam("image") MultipartFile image) {
        try {
            log.info("📸 Received plant identification request - File: {}, Size: {}, Type: {}", 
                image.getOriginalFilename(), image.getSize(), image.getContentType());
            
            if (image.isEmpty()) {
                log.warn("⚠️ Empty image file received");
                return ResponseEntity.badRequest().body("Please upload an image");
            }

            // Validate file type
            String contentType = image.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                log.warn("⚠️ Invalid file type: {}", contentType);
                return ResponseEntity.badRequest().body("Please upload a valid image file");
            }

            log.info("🔍 Calling PlantNet service...");
            PlantIdentificationResponse result = plantNetService.identifyPlant(image);
            log.info("✅ Identification successful: {}", result.getCommonName());
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            log.error("❌ Error identifying plant: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error identifying plant: " + e.getMessage());
        }
    }
}
