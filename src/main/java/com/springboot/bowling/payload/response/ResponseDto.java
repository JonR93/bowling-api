package com.springboot.bowling.payload.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Abstract Response Dto class to be extended by all response Dtos
 *
 * Contains the status string
 */
@Getter
@SuperBuilder
public abstract class ResponseDto {
    @ApiModelProperty(value = "HTTP status")
    private String status;
}
