package com.example.deviceregistryapi.exception;

/**
 * This exception is thrown when a requested resource is not found.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
