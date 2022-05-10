package com.springboot.bowling.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Player's current score
 */

@ApiModel(description = "Player's current score")
@Data
@AllArgsConstructor
public class ScoreDto {

    @ApiModelProperty(value = "Player's current score")
    private int score;
}
