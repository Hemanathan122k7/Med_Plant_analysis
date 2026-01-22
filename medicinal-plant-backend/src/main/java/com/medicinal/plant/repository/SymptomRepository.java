package com.medicinal.plant.repository;

import com.medicinal.plant.model.entity.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Symptom Repository
 */
@Repository
public interface SymptomRepository extends JpaRepository<Symptom, Long> {

    Optional<Symptom> findByName(String name);
}
