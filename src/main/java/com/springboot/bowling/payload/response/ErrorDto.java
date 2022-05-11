package com.springboot.bowling.payload.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * Error DTO object for sending errors/exceptions to the client
 */

@ApiModel(description = "Generic error response Dto")
@Getter
@SuperBuilder
public class ErrorDto extends ResponseDto{
    @ApiModelProperty(value = "Error timestamp")
    private Date timestamp;
    @ApiModelProperty(value = "Error message")
    private String message;
    @ApiModelProperty(value = "Error message details")
    private String details;
}
