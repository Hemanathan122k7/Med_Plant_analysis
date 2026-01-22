package com.medicinal.plant.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Visual Search Request DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisualSearchRequest {
    private String leafShape;
    private String flowerColor;
    private String plantType;
    private String size;
}
