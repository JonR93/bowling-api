package com.springboot.bowling.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@ApiModel(description = "Request object to send a user id to the server to start a new game")
@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StartGameDto {
    @ApiModelProperty(value = "Player id")
    private Long id;
}
