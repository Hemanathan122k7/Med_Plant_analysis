package com.medicinal.plant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantIdentificationResponse {
    private String scientificName;
    private String commonName;
    private Double confidence;
    private String family;
    private String genus;
    private List<String> medicinalUses;
    private String description;
    private String dosage;
    private String precautions;
    private List<String> symptoms;
}
