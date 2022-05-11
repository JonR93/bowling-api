package com.springboot.bowling.controller;

import com.springboot.bowling.entity.Scoresheet;
import com.springboot.bowling.payload.request.StartGameDto;
import com.springboot.bowling.payload.request.ThrowBallDto;
import com.springboot.bowling.payload.response.*;
import com.springboot.bowling.service.BowlingAlleyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Api(value = "Bowling Alley endpoints")
@RestController
@RequestMapping
@AllArgsConstructor
public class BowlingAlleyController {

    private BowlingAlleyService bowlingAlleyService;

    /**
     * Start a new game for a given player id
     * @param startGameDto
     * @return HttpStatus.OK status code 200
     */
    @ApiOperation(value = "Start a new game for a given player")
    @PostMapping("/startGame")
    public ResponseEntity<ResponseDto> startGame(@Valid @RequestBody StartGameDto startGameDto) {
        bowlingAlleyService.startGame(startGameDto.getId());

        GameStatedDto gameStatedDto = GameStatedDto.builder()
                                        .status(HttpStatus.OK.getReasonPhrase())
                                        .build();

        return new ResponseEntity<>(gameStatedDto, HttpStatus.OK);
    }

    /**
     * Throw a ball for a player
     * @param throwBallDto
     * @return HttpStatus.OK status code 200
     */
    @ApiOperation(value = "Throw a ball for a player")
    @PostMapping("/throwBall")
    public ResponseEntity<ResponseDto> throwBall(@Valid @RequestBody ThrowBallDto throwBallDto){
        bowlingAlleyService.throwBall(throwBallDto.getId(),
                throwBallDto.getFrameIndex(),
                throwBallDto.getBallIndex(),
                throwBallDto.getScore());

        BallThrownDto ballThrownDto = BallThrownDto.builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .build();

        return new ResponseEntity<>(ballThrownDto, HttpStatus.OK);
    }

    /**
     * Get a player's current score
     * @param playerId
     * @return HttpStatus.OK status code 200 and Player's current score
     */
    @ApiOperation(value = "Get a player's current score")
    @GetMapping("/score/{playerId}")
    public ResponseEntity<ResponseDto> getPlayerScore(@PathVariable(name = "playerId") UUID playerId){
        int currentPlayerScore = bowlingAlleyService.getPlayersCurrentScore(playerId);

        ScoreDto scoreDto = ScoreDto.builder()
                .score(currentPlayerScore)
                .status(HttpStatus.OK.getReasonPhrase())
                .build();

        return new ResponseEntity<>(scoreDto, HttpStatus.OK);
    }

    /**
     * Get a player's current scoresheet
     * @param playerId
     * @return HttpStatus.OK status code 200 and Player's current scoresheet
     */
    @ApiOperation(value = "Get a player's current scoresheet")
    @GetMapping("/scoresheet/{playerId}")
    public ResponseEntity<ResponseDto> getPlayerScoresheet(@PathVariable(name = "playerId") UUID playerId){
        Scoresheet playersScoresheet = bowlingAlleyService.getPlayersScoresheet(playerId);

        ScoresheetDto scoresheetDto = ScoresheetDto.builder()
                .sheet(playersScoresheet.toString())
                .status(HttpStatus.OK.getReasonPhrase())
                .build();

        return new ResponseEntity<>(scoresheetDto, HttpStatus.OK);
    }

}
