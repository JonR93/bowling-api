package com.springboot.bowling.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@ApiModel(description = "A ball thrown by a player")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThrowBallDto {
    @ApiModelProperty(value = "Player id")
    @NotEmpty
    private Long id;
    @ApiModelProperty(value = "Scoresheet Frame index")
    @NotEmpty
    @Size(min = 0, max = 9)
    private int frameIndex;
    @ApiModelProperty(value = "Ball index indicating which ball throw this is during this frame")
    @NotEmpty
    @Size(min = 0, max = 2)
    private int ballIndex;
    @ApiModelProperty(value = "Number of pins knocked down this throw")
    @NotEmpty
    @Size(min = 0,max = 10)
    private int score;
}
