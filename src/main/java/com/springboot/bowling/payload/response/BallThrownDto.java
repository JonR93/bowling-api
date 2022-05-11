package com.springboot.bowling.payload.response;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Response DTO returned when a ball is thrown"
 */

@ApiModel(description = "Response DTO returned when a ball is thrown")
@Getter
@SuperBuilder
public class BallThrownDto extends ResponseDto{
}
