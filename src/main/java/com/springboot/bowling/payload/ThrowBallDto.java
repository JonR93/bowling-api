package com.springboot.bowling.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@ApiModel(description = "A ball thrown by a player")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThrowBallDto {
    @ApiModelProperty(value = "Player id")
    private Long id;
    @ApiModelProperty(value = "Scoresheet Frame index")

    @Min(0)
    @Max(9)
    private int frameIndex;
    @ApiModelProperty(value = "Ball index indicating which ball throw this is during this frame")

    @Min(0)
    @Max(2)
    private int ballIndex;
    @ApiModelProperty(value = "Number of pins knocked down this throw")

    @Min(0)
    @Max(10)
    private int score;
}
