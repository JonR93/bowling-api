package com.springboot.bowling.service;

import com.springboot.bowling.entity.Scoresheet;
import com.springboot.bowling.payload.ScoreDto;
import com.springboot.bowling.payload.ScoresheetDto;

/**
 * Define Game functionality
 *
 * - start a game
 * - throw a ball
 * - perform scoring logic
 */
public interface GameService {
    Scoresheet generateNewScoresheet();
    void startGame(Long playerId);
    void throwBall(Long playerId, int frameIndex, int ballIndex, int score);
    ScoreDto getPlayersCurrentScore(Long playerId);
    ScoresheetDto getPlayersScoresheet(Long playerId);
}
