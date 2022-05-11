package com.springboot.bowling.payload.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Player's current score
 */

@ApiModel(description = "Response DTO representing the player's current score")
@Getter
@SuperBuilder
public class ScoreDto extends ResponseDto{
    @ApiModelProperty(value = "Player's current score")
    private int score;
}
