package com.medicinal.plant.controller;

import com.medicinal.plant.model.dto.response.ApiResponse;
import com.medicinal.plant.model.dto.response.ImageRecognitionResponse;
import com.medicinal.plant.service.ImageRecognitionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Image Controller
 */
@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
@Tag(name = "Images", description = "Image processing and recognition endpoints")
@CrossOrigin(origins = "*")
public class ImageController {

    private final ImageRecognitionService imageRecognitionService;

    @PostMapping("/recognize")
    @Operation(summary = "Recognize plant from image")
    public ResponseEntity<ApiResponse<ImageRecognitionResponse>> recognizePlant(
            @RequestParam("image") MultipartFile image) {
        ImageRecognitionResponse result = imageRecognitionService.recognizePlant(image);
        return ResponseEntity.ok(ApiResponse.success("Image processed successfully", result));
    }

    @PostMapping("/upload")
    @Operation(summary = "Upload plant image")
    public ResponseEntity<ApiResponse<String>> uploadImage(
            @RequestParam("image") MultipartFile image,
            @RequestParam("plantId") Long plantId) {
        String imageUrl = imageRecognitionService.uploadPlantImage(image, plantId);
        return ResponseEntity.ok(ApiResponse.success("Image uploaded successfully", imageUrl));
    }
}
