package com.medicinal.plant.model.enums;

/**
 * Safety Level Enum
 */
public enum SafetyLevel {
    SAFE(5),
    GENERALLY_SAFE(4),
    USE_WITH_CAUTION(3),
    POTENTIALLY_HARMFUL(2),
    TOXIC(1);

    private final int rating;

    SafetyLevel(int rating) {
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }
}
