package com.medicinal.plant.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Symptom Search Request DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SymptomSearchRequest {
    private List<String> symptoms;
    private String description; // Natural language description of symptoms
}
