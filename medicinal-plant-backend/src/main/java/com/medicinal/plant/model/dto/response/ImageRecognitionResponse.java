package com.medicinal.plant.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Image Recognition Response DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageRecognitionResponse {
    private List<PlantMatch> matches;
    private String imageUrl;
    private Double confidence;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PlantMatch {
        private Long plantId;
        private String plantName;
        private String scientificName;
        private Double matchScore;
        private String imageUrl;
    }
}
