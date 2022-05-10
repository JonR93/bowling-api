package com.springboot.bowling.payload.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Response DTO returned when a new player is added
 */

@ApiModel(description = "Response DTO returned when a new player is added")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerAddedDto {
    @ApiModelProperty(value = "HTTP status")
    private String status;

    @ApiModelProperty(value = "Id of the newly added player")
    private UUID id;
}
