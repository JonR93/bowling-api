package com.springboot.bowling.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * Error DTO object for sending errors/exceptions to the client
 */

@Data
@AllArgsConstructor
public class ErrorDto {
    private Date timestamp;
    private String message;
    private String details;
}
