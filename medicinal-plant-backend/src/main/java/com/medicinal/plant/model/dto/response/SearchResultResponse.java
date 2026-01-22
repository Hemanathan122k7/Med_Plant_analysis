package com.medicinal.plant.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Search Result Response DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchResultResponse {
    private List<PlantResponse> plants;
    private Integer totalResults;
    private String searchType;
    private String searchQuery;
    private String aiRecommendation; // AI-generated recommendation for symptom searches
}
