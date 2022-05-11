package com.springboot.bowling.controller;

import com.springboot.bowling.entity.Player;
import com.springboot.bowling.payload.request.DeletePlayerDto;
import com.springboot.bowling.payload.request.NewPlayerDto;
import com.springboot.bowling.payload.response.PlayerAddedDto;
import com.springboot.bowling.payload.response.PlayerDeletedDto;
import com.springboot.bowling.service.PlayerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Player Endpoints
 */

@Api(value = "Player creation and deletion endpoints")
@RestController
@RequestMapping("/player")
@AllArgsConstructor
public class PlayerController {

    private PlayerService playerService;

    /**
     * Add a new player
     * @param playerDto
     * @return if player added successfully, return the newly added player info and status code 201
     */
    @ApiOperation(value = "Add a new Player")
    @PostMapping
    public ResponseEntity<PlayerAddedDto> addPlayer(@Valid @RequestBody NewPlayerDto playerDto){
        Player newPlayer = playerService.addPlayer(playerDto);

        PlayerAddedDto playerAddedDto = PlayerAddedDto.builder()
                                        .id(newPlayer.getId())
                                        .status(HttpStatus.OK.getReasonPhrase())
                                        .build();

        return new ResponseEntity<>(playerAddedDto, HttpStatus.CREATED);
    }

    /**
     * Delete a player
     * @param deletePlayerDto
     * @return if player is deleted successfully, return status code 200
     */
    @ApiOperation(value = "Delete a Player by id")
    @DeleteMapping
    public ResponseEntity<PlayerDeletedDto> deletePlayer(@Valid @RequestBody DeletePlayerDto deletePlayerDto){
        playerService.deletePlayer(deletePlayerDto.getId());

        PlayerDeletedDto playerDeletedDto = PlayerDeletedDto.builder()
                                            .status(HttpStatus.OK.getReasonPhrase())
                                            .build();

        return new ResponseEntity<>(playerDeletedDto, HttpStatus.OK);
    }
}
