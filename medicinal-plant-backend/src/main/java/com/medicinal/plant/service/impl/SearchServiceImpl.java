package com.medicinal.plant.service.impl;

import com.medicinal.plant.model.dto.request.PlantSearchRequest;
import com.medicinal.plant.model.dto.request.SymptomSearchRequest;
import com.medicinal.plant.model.dto.request.VisualSearchRequest;
import com.medicinal.plant.model.dto.response.PlantResponse;
import com.medicinal.plant.model.dto.response.SearchResultResponse;
import com.medicinal.plant.service.PlantService;
import com.medicinal.plant.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Search Service Implementation
 */
@Service
@Slf4j
public class SearchServiceImpl implements SearchService {

    private final PlantService plantService;

    public SearchServiceImpl(PlantService plantService) {
        this.plantService = plantService;
    }

    @Override
    public SearchResultResponse performGeneralSearch(PlantSearchRequest request) {
        List<PlantResponse> plants = plantService.searchPlants(request.getQuery());
        return SearchResultResponse.builder()
                .plants(plants)
                .totalResults(plants.size())
                .searchType(request.getSearchType())
                .searchQuery(request.getQuery())
                .build();
    }

    @Override
    public SearchResultResponse searchBySymptoms(SymptomSearchRequest request) {
        // Natural language symptom search
        if (request.getDescription() != null && !request.getDescription().trim().isEmpty()) {
            String description = request.getDescription().toLowerCase();
            log.info("Symptom search for: {}", description);
            
            // Extract potential symptom keywords from natural language
            String[] keywords = extractSymptomKeywords(description);
            log.info("Extracted keywords: {}", String.join(", ", keywords));
            
            // Search for plants matching any of the extracted keywords
            List<PlantResponse> allPlants = new java.util.ArrayList<>();
            java.util.Set<Long> addedPlantIds = new java.util.HashSet<>();
            
            for (String keyword : keywords) {
                List<PlantResponse> plants = plantService.searchPlants(keyword);
                for (PlantResponse plant : plants) {
                    if (!addedPlantIds.contains(plant.getId())) {
                        allPlants.add(plant);
                        addedPlantIds.add(plant.getId());
                    }
                }
            }
            
            log.info("Found {} plants for query: {}", allPlants.size(), description);
            
            return SearchResultResponse.builder()
                    .plants(allPlants)
                    .totalResults(allPlants.size())
                    .searchType("BY_SYMPTOM")
                    .searchQuery(request.getDescription())
                    .build();
        }
        
        // Fallback to old keyword-based search if description is empty
        if (request.getSymptoms() != null && !request.getSymptoms().isEmpty()) {
            List<PlantResponse> plants = plantService.searchPlants(request.getSymptoms().get(0));
            return SearchResultResponse.builder()
                    .plants(plants)
                    .totalResults(plants.size())
                    .searchType("BY_SYMPTOM")
                    .searchQuery(String.join(", ", request.getSymptoms()))
                    .build();
        }
        
        return SearchResultResponse.builder()
                .plants(List.of())
                .totalResults(0)
                .searchType("BY_SYMPTOM")
                .searchQuery("")
                .build();
    }
    
    /**
     * Extract symptom keywords from natural language description
     */
    private String[] extractSymptomKeywords(String description) {
        // Common symptom-related words
        String[] commonSymptoms = {
            "headache", "head", "pain", "ache", "migraine",
            "stomach", "ulcer", "digestive", "digestion", "nausea", "vomit",
            "stress", "anxiety", "anxious", "nervous", "worry",
            "cold", "flu", "cough", "fever", "temperature",
            "skin", "rash", "irritation", "burn", "wound", "cut",
            "sleep", "insomnia", "tired", "fatigue", "weak",
            "joint", "muscle", "inflammation", "swelling",
            "blood pressure", "cholesterol", "heart",
            "infection", "bacteria", "virus",
            "respiratory", "breathing", "asthma",
            "depression", "sad", "mood"
        };
        
        java.util.List<String> foundKeywords = new java.util.ArrayList<>();
        String lowerDesc = description.toLowerCase();
        
        for (String symptom : commonSymptoms) {
            if (lowerDesc.contains(symptom)) {
                foundKeywords.add(symptom);
            }
        }
        
        // If no specific symptoms found, use the whole description as keywords
        if (foundKeywords.isEmpty()) {
            // Split by common words and punctuation
            String[] words = lowerDesc.split("[\\s,;.!?]+");
            for (String word : words) {
                if (word.length() > 3) { // Only words longer than 3 characters
                    foundKeywords.add(word);
                }
            }
        }
        
        return foundKeywords.toArray(new String[0]);
    }

    @Override
    public SearchResultResponse searchByVisualFeatures(VisualSearchRequest request) {
        // Simplified: Return all plants for demo
        List<PlantResponse> plants = plantService.getAllPlants();
        return SearchResultResponse.builder()
                .plants(plants)
                .totalResults(plants.size())
                .searchType("BY_VISUAL")
                .searchQuery("Visual features search")
                .build();
    }
}

