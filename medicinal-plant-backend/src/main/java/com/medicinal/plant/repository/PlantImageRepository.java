package com.medicinal.plant.repository;

import com.medicinal.plant.model.entity.PlantImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Plant Image Repository
 */
@Repository
public interface PlantImageRepository extends JpaRepository<PlantImage, Long> {

    List<PlantImage> findByPlantId(Long plantId);

    List<PlantImage> findByPlantIdAndIsPrimaryTrue(Long plantId);
}
