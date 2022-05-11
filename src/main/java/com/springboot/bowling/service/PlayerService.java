package com.springboot.bowling.service;

import com.springboot.bowling.entity.Player;
import com.springboot.bowling.payload.request.NewPlayerDto;

import java.util.UUID;

/**
 * Define Player functionality
 *
 * - add a player
 * - delete a player
 */

public interface PlayerService {
    Player addPlayer(NewPlayerDto playerDto);
    void deletePlayer(UUID id);
}
