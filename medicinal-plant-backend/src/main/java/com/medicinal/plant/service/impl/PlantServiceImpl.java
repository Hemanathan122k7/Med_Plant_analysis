package com.medicinal.plant.service.impl;

import com.medicinal.plant.exception.PlantNotFoundException;
import com.medicinal.plant.model.dto.response.PlantResponse;
import com.medicinal.plant.model.entity.Plant;
import com.medicinal.plant.repository.PlantRepository;
import com.medicinal.plant.service.PlantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Plant Service Implementation
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PlantServiceImpl implements PlantService {

    private final PlantRepository plantRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<PlantResponse> getAllPlants() {
        // Return dummy data if database is empty
        List<Plant> plants = plantRepository.findAll();
        if (plants.isEmpty()) {
            return getDummyPlants();
        }
        return plants.stream()
                .map(plant -> modelMapper.map(plant, PlantResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public PlantResponse getPlantById(Long id) {
        Plant plant = plantRepository.findById(id)
                .orElseThrow(() -> new PlantNotFoundException("Plant not found with id: " + id));
        return modelMapper.map(plant, PlantResponse.class);
    }

    @Override
    public List<PlantResponse> searchPlants(String query) {
        log.info("Searching plants with query: {}", query);
        List<Plant> plants = plantRepository.searchByKeyword(query);
        log.info("Repository returned {} plants", plants.size());
        if (!plants.isEmpty()) {
            log.info("First plant found: {}", plants.get(0).getName());
        }
        return plants.stream()
                .map(plant -> modelMapper.map(plant, PlantResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PlantResponse> getTopRatedPlants() {
        List<Plant> plants = plantRepository.findTopRatedPlants(4.0);
        return plants.stream()
                .map(plant -> modelMapper.map(plant, PlantResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PlantResponse> getPlantsByType(String type) {
        List<Plant> plants = plantRepository.findByPlantType(type.toUpperCase());
        return plants.stream()
                .map(plant -> modelMapper.map(plant, PlantResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deletePlant(Long id) {
        if (!plantRepository.existsById(id)) {
            throw new PlantNotFoundException("Plant not found with id: " + id);
        }
        plantRepository.deleteById(id);
    }

    /**
     * Returns dummy plant data for demonstration
     */
    private List<PlantResponse> getDummyPlants() {
        return Arrays.asList(
                PlantResponse.builder()
                        .id(1L)
                        .name("Aloe Vera")
                        .scientificName("Aloe barbadensis miller")
                        .description("Aloe vera is a succulent plant species known for its medicinal properties.")
                        .plantType("SUCCULENT")
                        .medicinalUses(Arrays.asList("Burns", "Skin care", "Digestive health"))
                        .activeCompounds(Arrays.asList("Aloin", "Acemannan"))
                        .precautions("May cause allergic reactions in some individuals")
                        .dosage("Apply gel topically or consume 1-2 tablespoons of juice daily")
                        .safetyRating("SAFE")
                        .availability("Widely available")
                        .rating(4.5)
                        .reviewCount(120)
                        .properties(Arrays.asList("anti-inflammatory", "moisturizing"))
                        .build(),
                PlantResponse.builder()
                        .id(2L)
                        .name("Turmeric")
                        .scientificName("Curcuma longa")
                        .description("Turmeric is a flowering plant with powerful anti-inflammatory properties.")
                        .plantType("HERB")
                        .medicinalUses(Arrays.asList("Inflammation", "Pain relief", "Arthritis"))
                        .activeCompounds(Arrays.asList("Curcumin"))
                        .precautions("May interact with blood thinners")
                        .dosage("500-2000mg daily")
                        .safetyRating("GENERALLY_SAFE")
                        .availability("Common")
                        .rating(4.7)
                        .reviewCount(250)
                        .properties(Arrays.asList("anti-inflammatory", "antioxidant"))
                        .build(),
                PlantResponse.builder()
                        .id(3L)
                        .name("Peppermint")
                        .scientificName("Mentha piperita")
                        .description("Peppermint is a hybrid mint known for its refreshing aroma and digestive benefits.")
                        .plantType("HERB")
                        .medicinalUses(Arrays.asList("Digestive issues", "Headaches", "Nausea"))
                        .activeCompounds(Arrays.asList("Menthol", "Menthone"))
                        .precautions("Avoid if you have GERD")
                        .dosage("1-2 cups of tea daily")
                        .safetyRating("SAFE")
                        .availability("Very common")
                        .rating(4.6)
                        .reviewCount(180)
                        .properties(Arrays.asList("cooling", "digestive"))
                        .build(),
                PlantResponse.builder()
                        .id(4L)
                        .name("Lavender")
                        .scientificName("Lavandula angustifolia")
                        .description("Lavender is famous for its calming and relaxing properties.")
                        .plantType("HERB")
                        .medicinalUses(Arrays.asList("Anxiety", "Insomnia", "Stress relief"))
                        .activeCompounds(Arrays.asList("Linalool", "Linalyl acetate"))
                        .precautions("May cause drowsiness")
                        .dosage("Use essential oil or drink 1 cup of tea")
                        .safetyRating("SAFE")
                        .availability("Common")
                        .rating(4.8)
                        .reviewCount(300)
                        .properties(Arrays.asList("calming", "aromatic"))
                        .build(),
                PlantResponse.builder()
                        .id(5L)
                        .name("Ginger")
                        .scientificName("Zingiber officinale")
                        .description("Ginger root is widely used for nausea and digestive problems.")
                        .plantType("HERB")
                        .medicinalUses(Arrays.asList("Nausea", "Digestion", "Cold symptoms"))
                        .activeCompounds(Arrays.asList("Gingerol"))
                        .precautions("May interact with blood thinners")
                        .dosage("1-3 grams daily")
                        .safetyRating("GENERALLY_SAFE")
                        .availability("Very common")
                        .rating(4.7)
                        .reviewCount(220)
                        .properties(Arrays.asList("warming", "digestive"))
                        .build(),
                PlantResponse.builder()
                        .id(6L)
                        .name("Chamomile")
                        .scientificName("Matricaria chamomilla")
                        .description("Chamomile is a gentle herb known for promoting relaxation and sleep.")
                        .plantType("HERB")
                        .medicinalUses(Arrays.asList("Insomnia", "Anxiety", "Digestive issues"))
                        .activeCompounds(Arrays.asList("Apigenin", "Bisabolol"))
                        .precautions("Avoid if allergic to ragweed")
                        .dosage("1-3 cups of tea daily")
                        .safetyRating("SAFE")
                        .availability("Common")
                        .rating(4.5)
                        .reviewCount(190)
                        .properties(Arrays.asList("calming", "gentle"))
                        .build()
        );
    }
}
