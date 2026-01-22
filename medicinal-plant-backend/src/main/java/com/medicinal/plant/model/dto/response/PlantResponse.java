package com.medicinal.plant.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Plant Response DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlantResponse {
    private Long id;
    private String name;
    private String scientificName;
    private String description;
    private String plantType;
    private List<String> medicinalUses;
    private List<String> activeCompounds;
    private String precautions;
    private String dosage;
    private String safetyRating;
    private String availability;
    private String growingConditions;
    private String imageUrl;
    private Double rating;
    private Integer reviewCount;
    private List<String> properties;
}
