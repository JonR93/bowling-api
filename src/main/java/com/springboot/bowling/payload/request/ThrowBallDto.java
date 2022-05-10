package com.springboot.bowling.payload.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@ApiModel(description = "Request DTO representing a ball thrown by a player")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThrowBallDto {

    @ApiModelProperty(value = "Player id")
    @NotNull
    private UUID id;

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
