package com.springboot.bowling.payload.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * Error DTO object for sending errors/exceptions to the client
 */

@ApiModel(description = "Error Model Info")
@Data
@AllArgsConstructor
public class ErrorDto {
    @ApiModelProperty(value = "HTTP status")
    private String status;
    @ApiModelProperty(value = "Error timestamp")
    private Date timestamp;
    @ApiModelProperty(value = "Error message")
    private String message;
    @ApiModelProperty(value = "Error message details")
    private String details;
}
