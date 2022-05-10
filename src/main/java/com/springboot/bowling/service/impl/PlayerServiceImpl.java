package com.springboot.bowling.service.impl;

import com.springboot.bowling.entity.Player;
import com.springboot.bowling.exception.ResourceNotFoundException;
import com.springboot.bowling.payload.request.DeletePlayerDto;
import com.springboot.bowling.payload.request.NewPlayerDto;
import com.springboot.bowling.payload.response.PlayerAddedDto;
import com.springboot.bowling.payload.response.PlayerDeletedDto;
import com.springboot.bowling.repository.PlayerRepository;
import com.springboot.bowling.service.PlayerService;
import com.springboot.bowling.util.ObjectMapperUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * PlayerService Implementation
 */

@Service
@AllArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private PlayerRepository playerRepository;

    /**
     * Persists a new player to the database
     * @param playerDto
     * @return new Player DTO
     */
    @Override
    public PlayerAddedDto addPlayer(NewPlayerDto playerDto) {
        Player newPlayer = ObjectMapperUtil.map(playerDto, Player.class);
        Player savedPlayer = playerRepository.save(newPlayer);
        PlayerAddedDto playerAddedDto = ObjectMapperUtil.map(savedPlayer, PlayerAddedDto.class);
        playerAddedDto.setStatus(HttpStatus.OK.getReasonPhrase());
        return playerAddedDto;
    }

    /**
     * Deletes a player from the database
     * If the player attempting to be deleted is not found, then throw ResourceNotFoundException
     * @param id
     * @return
     */
    @Override
    public PlayerDeletedDto deletePlayer(UUID id) {
        Player player = playerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Player.class,"id",id));
        playerRepository.delete(player);
        return new PlayerDeletedDto(HttpStatus.OK.getReasonPhrase());
    }
}
