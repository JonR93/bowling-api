package com.springboot.bowling.payload.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Request DTO for deleting a player from the system
 */

@ApiModel(description = "Request DTO for deleting a player from the system")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeletePlayerDto {
    @ApiModelProperty(value = "Player id")
    @NotNull
    private UUID id;
}
