package com.medicinal.plant.service;

import com.medicinal.plant.dto.PlantIdentificationResponse;
import org.springframework.web.multipart.MultipartFile;

public interface PlantNetService {
    PlantIdentificationResponse identifyPlant(MultipartFile image) throws Exception;
}
