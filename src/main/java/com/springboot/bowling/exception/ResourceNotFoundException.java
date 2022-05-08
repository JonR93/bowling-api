package com.springboot.bowling.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.constraints.NotNull;

/**
 * Generic Resource Not Found Exception that can be thrown when
 * we attempt to get a resource from the database, but it was not found
 */
@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
    private final String resourceName;
    private final String fieldName;
    private final transient Object fieldValue;

    /**
     * ResourceNotFoundException all args constructor
     * @param clazz - Class of resource attempting to find
     * @param fieldName - field being used to look up the resource (typically the id)
     * @param fieldValue - actual value being used to find the resource
     */
    public ResourceNotFoundException(@NotNull Class clazz, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", clazz.getSimpleName(), fieldName, fieldValue));
        this.resourceName = clazz.getSimpleName();
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
