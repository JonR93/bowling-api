package com.springboot.bowling.payload.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Request object to send a user id to the server to start a new game
 */

@ApiModel(description = "Request object to send a user id to the server to start a new game")
@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StartGameDto {
    @ApiModelProperty(value = "Player id")
    @NotNull
    private UUID id;
}
