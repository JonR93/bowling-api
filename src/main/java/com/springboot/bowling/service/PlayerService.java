package com.springboot.bowling.service;

import com.springboot.bowling.payload.PlayerDto;

/**
 * Define Player functionality
 *
 * - add a player
 * - delete a player
 */

public interface PlayerService {
    PlayerDto addPlayer(PlayerDto playerDto);
    void deletePlayer(long id);
}
