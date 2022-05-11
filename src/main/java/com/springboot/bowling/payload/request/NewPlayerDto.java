package com.springboot.bowling.payload.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * Request DTO for adding a new player to the system
 */

@ApiModel(description = "Request DTO for adding a new player to the system")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewPlayerDto {
    @ApiModelProperty(value = "Player name")
    @NotEmpty
    private String name;
}
