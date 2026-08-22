package com.Springecom.EcomProject.exception;

public class ResourceNotFoundException extends RuntimeException {
    String resourceName;
    String field;
    String fieldName;
    Long fieldId;

    public ResourceNotFoundException() {

    }

    public ResourceNotFoundException(String message, String field, String fieldName, String resourceName) {
        super(String.format("%s not found with %s: %d",resourceName,field,fieldName));
        this.field = field;
        this.fieldName = fieldName;
        this.resourceName = resourceName;
    }

    public ResourceNotFoundException(String message, String field, Long fieldId, String resourceName) {
        super(String.format("%s not found with %s: %d",resourceName,field,fieldId));
        this.field = field;
        this.fieldId = fieldId;
        this.resourceName = resourceName;
    }
}
