package com.springboot.bowling.controller;

import com.springboot.bowling.payload.PlayerDto;
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
    public ResponseEntity<PlayerDto> addPlayer(@Valid @RequestBody PlayerDto playerDto){
        return new ResponseEntity<>(playerService.addPlayer(playerDto), HttpStatus.CREATED);
    }

    /**
     * Delete a player
     * @param id
     * @return if player is deleted successfully, return status code 200
     */
    @ApiOperation(value = "Delete a Player by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlayer(@PathVariable(name = "id") long id){
        playerService.deletePlayer(id);
        return new ResponseEntity<>("Player deleted successfully.", HttpStatus.OK);
    }
}
