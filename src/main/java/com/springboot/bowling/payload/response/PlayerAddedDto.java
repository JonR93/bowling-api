package com.springboot.bowling.payload.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

/**
 * Response DTO returned when a new player is added
 */

@ApiModel(description = "Response DTO returned when a new player is added")
@SuperBuilder
@Getter
public class PlayerAddedDto extends ResponseDto{
    @ApiModelProperty(value = "Id of the newly added player")
    private UUID id;
}
