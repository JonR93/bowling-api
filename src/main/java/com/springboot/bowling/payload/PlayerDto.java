package com.springboot.bowling.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * Player DTO
 */

@ApiModel(description = "Player Model Info")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDto {
    @ApiModelProperty(value = "Player id")
    private Long id;

    @ApiModelProperty(value = "Player name")
    @NotEmpty
    private String name;
}
