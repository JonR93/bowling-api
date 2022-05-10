package com.springboot.bowling.payload.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO returned when a game is started
 */

@ApiModel(description = "Response DTO returned when a game is started")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameStatedDto {
    @ApiModelProperty(value = "HTTP status")
    private String status;
}
