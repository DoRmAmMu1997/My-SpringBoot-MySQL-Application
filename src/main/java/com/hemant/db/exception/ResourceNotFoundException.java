package com.hemant.db.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, Integer id) {
        super(resourceName + " not found with id " + id);
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
