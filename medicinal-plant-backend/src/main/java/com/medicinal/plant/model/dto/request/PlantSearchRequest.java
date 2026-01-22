package com.medicinal.plant.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Plant Search Request DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlantSearchRequest {

    @NotBlank(message = "Search query is required")
    private String query;

    private String searchType;
}
