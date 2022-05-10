package com.springboot.bowling.service;

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
    void startGame(Long playerId);
    void throwBall(Long playerId, int frameIndex, int ballIndex, int score);
    ScoreDto getCurrentScore(Long playerId);
    ScoresheetDto getScoresheet(Long playerId);
}
