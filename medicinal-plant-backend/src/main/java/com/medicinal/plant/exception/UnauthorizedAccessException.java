package com.medicinal.plant.exception;

/**
 * Unauthorized Access Exception
 */
public class UnauthorizedAccessException extends RuntimeException {
    
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
