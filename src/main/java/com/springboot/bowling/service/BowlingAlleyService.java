package com.springboot.bowling.service;

import com.springboot.bowling.entity.Scoresheet;

import java.util.UUID;

/**
 * Define Game functionality
 *
 * - start a game
 * - throw a ball
 * - perform scoring logic
 */
public interface BowlingAlleyService {
    Scoresheet generateNewScoresheet();
    void startGame(UUID playerId);
    void throwBall(UUID playerId, int frameIndex, int ballIndex, int score);
    int getPlayersCurrentScore(UUID playerId);
    Scoresheet getPlayersScoresheet(UUID playerId);
}
