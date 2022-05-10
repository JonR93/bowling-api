package com.springboot.bowling.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Scoresheet DTO
 * Contains the player's name and their frame scores
 */

@ApiModel(description = "Player's scoresheet")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScoresheetDto {

    @ApiModelProperty(value = "Player's scoresheet")
    private String sheet;
}
