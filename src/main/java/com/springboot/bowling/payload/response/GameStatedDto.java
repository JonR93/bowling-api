package com.springboot.bowling.payload.response;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Response DTO returned when a game is started
 */

@ApiModel(description = "Response DTO returned when a game is started")
@Getter
@SuperBuilder
public class GameStatedDto extends ResponseDto{
}
