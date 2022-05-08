package com.springboot.bowling.service;

import com.springboot.bowling.payload.PlayerDto;

public interface PlayerService {
    PlayerDto addPlayer(PlayerDto playerDto);
    void deletePlayer(long id);
}
