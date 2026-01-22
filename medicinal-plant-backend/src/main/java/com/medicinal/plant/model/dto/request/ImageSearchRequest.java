package com.medicinal.plant.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Image Search Request DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageSearchRequest {
    private String imageUrl;
    private String imageData; // Base64 encoded
}
