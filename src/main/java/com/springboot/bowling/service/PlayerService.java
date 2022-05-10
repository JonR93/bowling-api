package com.springboot.bowling.service;

import com.springboot.bowling.payload.request.NewPlayerDto;
import com.springboot.bowling.payload.response.PlayerAddedDto;
import com.springboot.bowling.payload.response.PlayerDeletedDto;

import java.util.UUID;

/**
 * Define Player functionality
 *
 * - add a player
 * - delete a player
 */

public interface PlayerService {
    PlayerAddedDto addPlayer(NewPlayerDto playerDto);
    PlayerDeletedDto deletePlayer(UUID id);
}
