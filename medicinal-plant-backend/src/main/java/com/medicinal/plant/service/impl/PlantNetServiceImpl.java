package com.medicinal.plant.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medicinal.plant.dto.PlantIdentificationResponse;
import com.medicinal.plant.model.entity.Plant;
import com.medicinal.plant.repository.PlantRepository;
import com.medicinal.plant.service.PlantNetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlantNetServiceImpl implements PlantNetService {

    private final RestTemplate restTemplate;
    private final PlantRepository plantRepository;
    private final ObjectMapper objectMapper;

    @Value("${plantnet.api.key}")
    private String apiKey;

    @Value("${plantnet.api.url}")
    private String apiUrl;

    @Override
    public PlantIdentificationResponse identifyPlant(MultipartFile image) throws Exception {
        log.info("🔍 Identifying plant from uploaded image...");

        // Prepare the request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Create multipart body
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        
        // Add image file
        ByteArrayResource imageResource = new ByteArrayResource(image.getBytes()) {
            @Override
            public String getFilename() {
                return image.getOriginalFilename();
            }
        };
        body.add("images", imageResource);

        // Add organs parameter (can be leaf, flower, fruit, bark)
        body.add("organs", "auto");

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // Call PlantNet API
        String url = apiUrl + "?api-key=" + apiKey + "&include-related-images=false";
        
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String.class
            );

            log.info("✅ PlantNet API response received");

            // Parse response
            JsonNode root = objectMapper.readTree(response.getBody());
            
            if (root.has("results") && root.get("results").isArray() && root.get("results").size() > 0) {
                JsonNode firstResult = root.get("results").get(0);
                
                String scientificName = firstResult.get("species").get("scientificNameWithoutAuthor").asText();
                String genus = firstResult.get("species").get("genus").get("scientificNameWithoutAuthor").asText();
                String family = firstResult.get("species").get("family").get("scientificNameWithoutAuthor").asText();
                Double confidence = firstResult.get("score").asDouble() * 100;
                
                log.info("🌿 Identified plant: {} ({}% confidence)", scientificName, String.format("%.2f", confidence));

                // Try to find medicinal information from our database
                PlantIdentificationResponse response1 = new PlantIdentificationResponse();
                response1.setScientificName(scientificName);
                response1.setGenus(genus);
                response1.setFamily(family);
                response1.setConfidence(confidence);

                // Extract common name if available
                if (firstResult.get("species").has("commonNames") && 
                    firstResult.get("species").get("commonNames").isArray() &&
                    firstResult.get("species").get("commonNames").size() > 0) {
                    response1.setCommonName(firstResult.get("species").get("commonNames").get(0).asText());
                } else {
                    response1.setCommonName(scientificName);
                }

                // Search our database for medicinal information
                enrichWithMedicinalData(response1, scientificName);

                return response1;
            } else {
                throw new Exception("No plant identified from the image");
            }

        } catch (Exception e) {
            log.error("❌ Error calling PlantNet API: {}", e.getMessage());
            throw new Exception("Failed to identify plant: " + e.getMessage());
        }
    }

    private void enrichWithMedicinalData(PlantIdentificationResponse response, String scientificName) {
        // Try to find exact match first
        Optional<Plant> plantOpt = plantRepository.findAll().stream()
            .filter(p -> p.getScientificName() != null && 
                        p.getScientificName().toLowerCase().contains(scientificName.toLowerCase()))
            .findFirst();

        if (plantOpt.isEmpty()) {
            // Try to find by common name
            plantOpt = plantRepository.findAll().stream()
                .filter(p -> response.getCommonName() != null && 
                            p.getName().toLowerCase().contains(response.getCommonName().toLowerCase()))
                .findFirst();
        }

        if (plantOpt.isPresent()) {
            Plant plant = plantOpt.get();
            log.info("✅ Found medicinal data for: {}", plant.getName());
            
            response.setCommonName(plant.getName());
            response.setMedicinalUses(plant.getMedicinalUses());
            response.setDescription(plant.getDescription());
            response.setDosage(plant.getDosage());
            response.setPrecautions(plant.getPrecautions());
            
            // Extract symptoms from relationships
            List<String> symptoms = new ArrayList<>();
            if (plant.getPlantSymptoms() != null) {
                plant.getPlantSymptoms().forEach(rel -> {
                    if (rel.getSymptom() != null) {
                        symptoms.add(rel.getSymptom().getName());
                    }
                });
            }
            response.setSymptoms(symptoms);
        } else {
            log.warn("⚠️ No medicinal data found in database for: {}", scientificName);
            response.setDescription("Plant identified but medicinal information not available in our database.");
            response.setMedicinalUses(new ArrayList<>());
            response.setSymptoms(new ArrayList<>());
            response.setPrecautions("Consult a healthcare professional before use.");
        }
    }
}
