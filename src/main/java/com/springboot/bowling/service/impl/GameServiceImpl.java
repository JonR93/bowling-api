package com.springboot.bowling.service.impl;

import com.springboot.bowling.entity.Frame;
import com.springboot.bowling.entity.Player;
import com.springboot.bowling.entity.Scoresheet;
import com.springboot.bowling.exception.BadRequestException;
import com.springboot.bowling.exception.MissingScoresheetException;
import com.springboot.bowling.exception.ResourceNotFoundException;
import com.springboot.bowling.payload.response.GameStatedDto;
import com.springboot.bowling.payload.response.ScoreDto;
import com.springboot.bowling.payload.response.ScoresheetDto;
import com.springboot.bowling.repository.PlayerRepository;
import com.springboot.bowling.repository.ScoresheetRepository;
import com.springboot.bowling.service.GameService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * GameService Implementation
 */

@Service
@AllArgsConstructor
public class GameServiceImpl implements GameService {

    private PlayerRepository playerRepository;
    private ScoresheetRepository scoresheetRepository;

    /**
     * Give a player a new scoresheet with 10 blank frames
     * @param playerId
     * @return
     */
    @Override
    public GameStatedDto startGame(UUID playerId) {
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new ResourceNotFoundException(Player.class,"id",playerId));
        player.setScoreSheet(generateNewScoresheet());
        playerRepository.save(player);
        return new GameStatedDto(HttpStatus.OK.getReasonPhrase());
    }

    /**
     * Record the number of pins a player knocked over for a given ball throw
     * @param playerId
     * @param frameIndex
     * @param ballIndex
     * @param score
     */
    @Override
    public void throwBall(UUID playerId, int frameIndex, int ballIndex, int score) {
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new ResourceNotFoundException(Player.class,"id",playerId));
        Scoresheet scoresheet = Optional.ofNullable(player.getScoreSheet()).orElseThrow(() -> new MissingScoresheetException(player));

        if(frameIndex != scoresheet.getCurrentFrameIndex()){
            String msg = String.format("The provided frame index does not match the current frame index: %s.", scoresheet.getCurrentFrameIndex());
            throw new BadRequestException(msg);
        }

        Frame currentFrame = scoresheet.getFrames().get(frameIndex);

        recordFrameScore(currentFrame,ballIndex,score);
        recalculateScoresheet(scoresheet);

        if(currentFrame.isComplete() && scoresheet.getCurrentFrameIndex()<9){
            scoresheet.setCurrentFrameIndex(scoresheet.getCurrentFrameIndex()+1);
        }

        scoresheetRepository.save(scoresheet);
    }

    /**
     * Calculate a given player's current score
     * @param playerId
     * @return player's current score
     */
    @Override
    public ScoreDto getPlayersCurrentScore(UUID playerId) {
        int currentScore = 0;
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new ResourceNotFoundException(Player.class,"id",playerId));
        Scoresheet scoresheet = Optional.ofNullable(player.getScoreSheet()).orElseThrow(() -> new MissingScoresheetException(player));
        int currentFrameIndex = scoresheet.getCurrentFrameIndex();
        return new ScoreDto(HttpStatus.OK.getReasonPhrase(),scoresheet.getScoreAtFrame(currentFrameIndex));
    }

    /**
     * Retrieve the scoresheet of a given player
     * @param playerId
     * @return scoresheet of a given player
     */
    @Override
    public ScoresheetDto getPlayersScoresheet(UUID playerId) {
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new ResourceNotFoundException(Player.class,"id",playerId));
        Scoresheet scoresheet = Optional.ofNullable(player.getScoreSheet()).orElseThrow(() -> new MissingScoresheetException(player));
        return new ScoresheetDto(HttpStatus.OK.getReasonPhrase(),scoresheet.toString());
    }

    /**
     * Generate a new scoresheet to give to the player with 10 new frames
     * @return {@link Scoresheet}
     */
    @Override
    public Scoresheet generateNewScoresheet() {
        Scoresheet newScoreSheet = new Scoresheet();
        List<Frame> newFrames = new ArrayList<>();
        for(int i=0; i<10; i++){
            newFrames.add(Frame.builder().scoresheet(newScoreSheet).frameIndex(i).build());
        }
        newScoreSheet.setFrames(newFrames);
        return newScoreSheet;
    }

    /**
     * Record the number of pins knocked over for a given throw of a frame
     * @param frame
     * @param ballIndex
     * @param score
     */
    private void recordFrameScore(Frame frame, int ballIndex, int score){
        validateBallThrow(frame,ballIndex,score);
        switch (ballIndex) {
            case 0:
                frame.setFirstRoll(score);
                break;
            case 1:
                frame.setSecondRoll(score);
                break;
            case 2:
                frame.setThirdRoll(score);
                break;
            default:
                throw new BadRequestException("Slow down. You're throwing too many balls.");
        }
    }

    /**
     * Perform some basic validation checks to verify the score for this throw can be recorded
     * @param frame
     * @param ballIndex
     * @param score
     */
    private void validateBallThrow(Frame frame, int ballIndex, int score){
        if(frame.getCurrentBallIndex()==null){
            String msg = String.format("Exceeded maximum ball throws for this frame index: %s.", frame.getFrameIndex());
            throw new BadRequestException(msg);
        }
        if(!Integer.valueOf(ballIndex).equals(frame.getCurrentBallIndex())){
            String msg = String.format("The provided ball index does not match the current ball index: %s for frame index: %s.", frame.getCurrentBallIndex(),frame.getFrameIndex());
            throw new BadRequestException(msg);
        }
        if(score > frame.getRemainingNumberOfPins()){
            String msg = String.format("Attempted to knock over more pins than there are remaining. Only %s pins remain.", frame.getRemainingNumberOfPins());
            throw new BadRequestException(msg);
        }
    }

    /**
     * Total score
     * @return total score of all played frames
     */
    private int recalculateScoresheet(Scoresheet scoresheet) {
        int frameScore = 0;
        int totalScore = 0;
        Collections.sort(scoresheet.getFrames());
        for(Frame frame: scoresheet.getFrames()){
            Frame nextFrame = null;
            Frame nextNextFrame = null;
            if (frame.getFrameIndex()+1 < scoresheet.getFrames().size()){
                nextFrame = scoresheet.getFrames().get(frame.getFrameIndex()+1);
            }
            if (frame.getFrameIndex()+2 < scoresheet.getFrames().size()){
                nextNextFrame = scoresheet.getFrames().get(frame.getFrameIndex()+2);
            }
            frameScore = getFrameScore(frame,nextFrame,nextNextFrame);
            frame.setScore(frameScore);
            totalScore += frameScore;
        }
        return totalScore;
    }

    /**
     * Calculate the score for a given frame index
     * @param frame - current frame
     * @param nextFrame - next frame is needed to calculate bonus points
     * @param nextNextFrame - next frame is needed to calculate bonus points
     * @return frame score
     */
    private int getFrameScore(Frame frame, Frame nextFrame, Frame nextNextFrame) {
        if (frame == null) {
            return 0;
        }
        if (frame.isStrike()) {
            return 10 + strikeBonus(frame,nextFrame,nextNextFrame);
        }
        if (frame.isSpare()) {
            return 10 + spareBonus(frame,nextFrame);
        }
        return rollValue(frame.getFirstRoll()) + rollValue(frame.getSecondRoll());
    }

    /**
     * Calculate the Spare Bonus for a frame
     * Bonus is based on next frame
     * @param frame - current frame
     * @param nextFrame - next frame is needed to calculate bonus points
     * @return frame score with spare bonus calculated
     */
    private int spareBonus(Frame frame, Frame nextFrame) {
        if (frame.getFrameIndex() == 9) {
            return rollValue(frame.getThirdRoll());
        }
        return nextFrame == null ? 0 : rollValue(nextFrame.getFirstRoll());
    }

    /**
     * Calculate the Strike Bonus for a frame
     * Bonus is based on next frame
     * @param frame - current frame
     * @param nextFrame - next frame is needed to calculate bonus points
     * @param nextNextFrame - next frame is needed to calculate bonus points
     * @return frame score with strike bonus calculated
     */
    private int strikeBonus(Frame frame, Frame nextFrame, Frame nextNextFrame) {
        if (frame.getFrameIndex() == 9) {
            return rollValue(frame.getSecondRoll()) + rollValue(frame.getThirdRoll());
        }
        if (nextFrame == null) {
            return 0;
        }
        if (nextFrame.isStrike()) {
            if (frame.getFrameIndex() == 8) {
                return 10 + rollValue(nextFrame.getSecondRoll());
            }
            return nextNextFrame == null ? 0 : 10 + rollValue(nextNextFrame.getFirstRoll());
        }
        return rollValue(nextFrame.getFirstRoll()) + rollValue(nextFrame.getSecondRoll());
    }

    /**
     * Util method to help get the number of pins knocked down when calculating the score.
     * If a roll hasn't occured yet, its value will be null so use the value of '0' when calculating the score
     * @param roll
     * @return Number of pins knocked down during a roll. Return 0 if the roll hasn't happened yet.
     */
    private int rollValue(Integer roll){
        return Optional.ofNullable(roll).orElse(0);
    }
}
