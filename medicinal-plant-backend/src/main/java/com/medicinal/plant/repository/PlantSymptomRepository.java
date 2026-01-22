package com.medicinal.plant.repository;

import com.medicinal.plant.model.entity.PlantSymptom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Plant-Symptom Repository
 */
@Repository
public interface PlantSymptomRepository extends JpaRepository<PlantSymptom, Long> {
}
