package com.medicinal.plant.exception;

/**
 * User Not Found Exception
 */
public class UserNotFoundException extends RuntimeException {
    
    public UserNotFoundException(String message) {
        super(message);
    }
}
