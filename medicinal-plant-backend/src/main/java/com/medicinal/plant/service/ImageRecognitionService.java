package com.medicinal.plant.service;

import com.medicinal.plant.model.dto.response.ImageRecognitionResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * Image Recognition Service Interface
 */
public interface ImageRecognitionService {

    ImageRecognitionResponse recognizePlant(MultipartFile image);

    String uploadPlantImage(MultipartFile image, Long plantId);
}
