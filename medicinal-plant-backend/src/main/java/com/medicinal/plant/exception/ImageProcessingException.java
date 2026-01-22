package com.medicinal.plant.exception;

/**
 * Image Processing Exception
 */
public class ImageProcessingException extends RuntimeException {
    
    public ImageProcessingException(String message) {
        super(message);
    }
    
    public ImageProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
