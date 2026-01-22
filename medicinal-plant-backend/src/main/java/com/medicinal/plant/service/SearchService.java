package com.medicinal.plant.service;

import com.medicinal.plant.model.dto.request.PlantSearchRequest;
import com.medicinal.plant.model.dto.request.SymptomSearchRequest;
import com.medicinal.plant.model.dto.request.VisualSearchRequest;
import com.medicinal.plant.model.dto.response.SearchResultResponse;

/**
 * Search Service Interface
 */
public interface SearchService {

    SearchResultResponse performGeneralSearch(PlantSearchRequest request);

    SearchResultResponse searchBySymptoms(SymptomSearchRequest request);

    SearchResultResponse searchByVisualFeatures(VisualSearchRequest request);
}
