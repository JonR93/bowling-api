package com.springboot.bowling.payload.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Player's current score
 */

@ApiModel(description = "Response DTO representing the player's current score")
@Data
@AllArgsConstructor
public class ScoreDto {

    @ApiModelProperty(value = "HTTP status")
    private String status;

    @ApiModelProperty(value = "Player's current score")
    private int score;
}
