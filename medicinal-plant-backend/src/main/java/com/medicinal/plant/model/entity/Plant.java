package com.medicinal.plant.model.entity;

import com.medicinal.plant.model.enums.PlantType;
import com.medicinal.plant.model.enums.SafetyLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Plant Entity
 */
@Entity
@Table(name = "plants")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "scientific_name", unique = true)
    private String scientificName;

    @Column(length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "plant_type")
    private PlantType plantType;

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "plant_medicinal_uses", joinColumns = @JoinColumn(name = "plant_id"))
    @Column(name = "medicinal_use")
    private List<String> medicinalUses = new ArrayList<>();

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "plant_active_compounds", joinColumns = @JoinColumn(name = "plant_id"))
    @Column(name = "compound")
    private List<String> activeCompounds = new ArrayList<>();

    @Column(length = 1000)
    private String precautions;

    @Column(length = 500)
    private String dosage;

    @Enumerated(EnumType.STRING)
    @Column(name = "safety_rating")
    private SafetyLevel safetyRating;

    private String availability;

    @Column(name = "growing_conditions", length = 1000)
    private String growingConditions;

    @Column(name = "image_url")
    private String imageUrl;

    private Double rating;

    @Column(name = "review_count")
    private Integer reviewCount;

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "plant_properties", joinColumns = @JoinColumn(name = "plant_id"))
    @Column(name = "property")
    private List<String> properties = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlantSymptom> plantSymptoms = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlantImage> images = new ArrayList<>();

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
