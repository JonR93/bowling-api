package com.springboot.bowling.payload.response;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@ApiModel(description = "Response DTO returned when a ball is thrown")
@Getter
@SuperBuilder
public class PlayerDeletedDto extends ResponseDto{
}
