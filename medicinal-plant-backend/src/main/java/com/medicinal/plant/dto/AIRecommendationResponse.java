package com.medicinal.plant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AIRecommendationResponse {
    private Long plantId;
    private String plantName;
    private String scientificName;
    private String aiRecommendation;
    private String imageUrl;
    private String safetyRating;
    private Double rating;
}
