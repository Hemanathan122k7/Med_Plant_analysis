package com.medicinal.plant.repository;

import com.medicinal.plant.model.entity.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Plant Repository
 */
@Repository
public interface PlantRepository extends JpaRepository<Plant, Long> {

    Optional<Plant> findByName(String name);

    Optional<Plant> findByScientificName(String scientificName);

    @Query("SELECT DISTINCT p FROM Plant p " +
           "LEFT JOIN p.medicinalUses mu " +
           "LEFT JOIN p.plantSymptoms ps " +
           "LEFT JOIN ps.symptom s " +
           "WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(p.scientificName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(mu) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(s.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Plant> searchByKeyword(@Param("keyword") String keyword);

    @Query("SELECT p FROM Plant p JOIN p.medicinalUses mu WHERE LOWER(mu) LIKE LOWER(CONCAT('%', :use, '%'))")
    List<Plant> findByMedicinalUse(@Param("use") String use);

    List<Plant> findByPlantType(String plantType);

    @Query("SELECT p FROM Plant p WHERE p.rating >= :minRating ORDER BY p.rating DESC")
    List<Plant> findTopRatedPlants(@Param("minRating") Double minRating);
}
