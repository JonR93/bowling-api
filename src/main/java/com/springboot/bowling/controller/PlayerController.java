package com.springboot.bowling.controller;

import com.springboot.bowling.payload.PlayerDto;
import com.springboot.bowling.service.PlayerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Player Endpoints
 */

@RestController
@RequestMapping("/api/v1/player")
@AllArgsConstructor
public class PlayerController {

    private PlayerService playerService;

    /**
     * Add a new player
     * @param playerDto
     * @return if player added successfully, return the newly added player info and status code 201
     */
    @PostMapping
    public ResponseEntity<PlayerDto> addPlayer(@Valid @RequestBody PlayerDto playerDto){
        return new ResponseEntity<>(playerService.addPlayer(playerDto), HttpStatus.CREATED);
    }

    /**
     * Delete a player
     * @param id
     * @return if player is deleted successfully, return status code 200
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlayer(@PathVariable(name = "id") long id){
        playerService.deletePlayer(id);
        return new ResponseEntity<>("Player deleted successfully.", HttpStatus.OK);
    }
}
