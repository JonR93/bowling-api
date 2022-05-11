package com.springboot.bowling.payload.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Scoresheet DTO
 * Contains the player's name and their frame scores
 */

@ApiModel(description = "Response DTO representing the player's scoresheet")
@Getter
@SuperBuilder
public class ScoresheetDto extends ResponseDto{
    @ApiModelProperty(value = "Player's scoresheet")
    private String sheet;
}
