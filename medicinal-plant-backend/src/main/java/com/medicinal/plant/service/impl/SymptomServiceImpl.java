package com.medicinal.plant.service.impl;

import com.medicinal.plant.repository.SymptomRepository;
import com.medicinal.plant.service.SymptomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Symptom Service Implementation
 */
@Service
@RequiredArgsConstructor
public class SymptomServiceImpl implements SymptomService {

    @SuppressWarnings("unused") // Will be used when database integration is fully implemented
    private final SymptomRepository symptomRepository;

    @Override
    public List<String> getAllSymptoms() {
        // Return dummy symptoms
        return Arrays.asList(
                "Headache", "Fever", "Cough", "Digestive Issues",
                "Anxiety", "Insomnia", "Pain", "Inflammation"
        );
    }

    @Override
    public List<String> getSymptomCategories() {
        return Arrays.asList(
                "Respiratory", "Digestive", "Mental Health",
                "Pain Management", "Skin Conditions"
        );
    }
}
