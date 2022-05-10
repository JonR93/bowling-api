package com.springboot.bowling.payload.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "Response DTO returned when a ball is thrown")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDeletedDto {
    @ApiModelProperty(value = "HTTP status")
    private String status;
}
