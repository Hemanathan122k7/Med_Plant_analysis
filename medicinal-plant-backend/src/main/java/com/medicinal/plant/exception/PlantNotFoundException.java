package com.medicinal.plant.exception;

/**
 * Plant Not Found Exception
 */
public class PlantNotFoundException extends RuntimeException {
    
    public PlantNotFoundException(String message) {
        super(message);
    }
}
