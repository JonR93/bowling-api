package com.springboot.bowling.exception;

import com.springboot.bowling.entity.Player;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.constraints.NotNull;

@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MissingScoresheetException extends RuntimeException{

    /**
     * Print a message stating that the player is missing a scoresheet and to start a new game
     * @param player
     */
    public MissingScoresheetException(@NotNull Player player) {
        super(String.format("Player, %s, does not have a scoresheet. Start a new game.", player.getId()));
    }
}
