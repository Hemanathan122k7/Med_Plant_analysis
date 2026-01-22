package com.medicinal.plant.repository;

import com.medicinal.plant.model.entity.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Search History Repository
 */
@Repository
public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {

    List<SearchHistory> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<SearchHistory> findTop10ByUserIdOrderByCreatedAtDesc(Long userId);
}
