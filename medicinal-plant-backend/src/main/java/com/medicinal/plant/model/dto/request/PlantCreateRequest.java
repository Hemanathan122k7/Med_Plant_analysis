package com.medicinal.plant.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Plant Create Request DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlantCreateRequest {

    @NotBlank(message = "Plant name is required")
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
}
