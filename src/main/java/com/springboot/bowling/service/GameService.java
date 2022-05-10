package com.springboot.bowling.service;

import com.springboot.bowling.entity.Scoresheet;
import com.springboot.bowling.payload.response.GameStatedDto;
import com.springboot.bowling.payload.response.ScoreDto;
import com.springboot.bowling.payload.response.ScoresheetDto;

import java.util.UUID;

/**
 * Define Game functionality
 *
 * - start a game
 * - throw a ball
 * - perform scoring logic
 */
public interface GameService {
    Scoresheet generateNewScoresheet();
    GameStatedDto startGame(UUID playerId);
    void throwBall(UUID playerId, int frameIndex, int ballIndex, int score);
    ScoreDto getPlayersCurrentScore(UUID playerId);
    ScoresheetDto getPlayersScoresheet(UUID playerId);
}
