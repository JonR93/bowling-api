package com.springboot.bowling.controller;

import com.springboot.bowling.payload.*;
import com.springboot.bowling.service.GameService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "Bowling Alley endpoints")
@RestController
@RequestMapping
@AllArgsConstructor
public class BowlingAlleyController {

    private GameService gameService;

    /**
     * Start a new game for a given player id
     * @param startGameDto
     * @return HttpStatus.OK status code 200
     */
    @ApiOperation(value = "Start a new game for a given player")
    @PostMapping("/startGame")
    public ResponseEntity<String> startGame(@Valid @RequestBody StartGameDto startGameDto) {
        gameService.startGame(startGameDto.getId());
        return new ResponseEntity<>("New game started.", HttpStatus.OK);
    }

    /**
     * Throw a ball for a player
     * @param throwBallDto
     * @return HttpStatus.OK status code 200
     */
    @ApiOperation(value = "Throw a ball for a player")
    @PostMapping("/throwBall")
    public ResponseEntity<String> throwBall(@Valid @RequestBody ThrowBallDto throwBallDto){
        gameService.throwBall(throwBallDto.getId(),
                throwBallDto.getFrameIndex(),
                throwBallDto.getBallIndex(),
                throwBallDto.getScore());
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    /**
     * Get a player's current score
     * @param playerId
     * @return HttpStatus.OK status code 200 and Player's current score
     */
    @ApiOperation(value = "Get a player's current score")
    @GetMapping("/score/{playerId}")
    public ResponseEntity<ScoreDto> getPlayerScore(@PathVariable(name = "playerId") Long playerId){
        return new ResponseEntity<>(gameService.getPlayersCurrentScore(playerId), HttpStatus.OK);
    }

    /**
     * Get a player's current scoresheet
     * @param playerId
     * @return HttpStatus.OK status code 200 and Player's current scoresheet
     */
    @ApiOperation(value = "Get a player's current scoresheet")
    @GetMapping("/scoresheet/{playerId}")
    public ResponseEntity<ScoresheetDto> getPlayerScoresheet(@PathVariable(name = "playerId") Long playerId){
        return new ResponseEntity<>(gameService.getPlayersScoresheet(playerId), HttpStatus.OK);
    }

}
