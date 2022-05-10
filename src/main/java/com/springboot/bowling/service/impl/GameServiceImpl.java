package com.springboot.bowling.service.impl;

import com.springboot.bowling.entity.Frame;
import com.springboot.bowling.entity.Player;
import com.springboot.bowling.entity.Scoresheet;
import com.springboot.bowling.exception.BadRequestException;
import com.springboot.bowling.exception.MissingScoresheetException;
import com.springboot.bowling.exception.ResourceNotFoundException;
import com.springboot.bowling.payload.ScoreDto;
import com.springboot.bowling.payload.ScoresheetDto;
import com.springboot.bowling.payload.ThrowBallDto;
import com.springboot.bowling.repository.PlayerRepository;
import com.springboot.bowling.service.GameService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * GameService Implementation
 */

@Service
@AllArgsConstructor
public class GameServiceImpl implements GameService {

    private PlayerRepository playerRepository;

    /**
     * Give a player a new scoresheet
     * @param playerId
     */
    @Override
    public void startGame(Long playerId) {
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new ResourceNotFoundException(Player.class,"id",playerId));
        player.setScoreSheet(new Scoresheet());
        playerRepository.save(player);
    }

    /**
     * Record the number of pins a player knocked over for a given ball throw
     * @param playerId
     * @param frameIndex
     * @param ballIndex
     * @param score
     */
    @Override
    public void throwBall(Long playerId, int frameIndex, int ballIndex, int score) {
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new ResourceNotFoundException(Player.class,"id",playerId));
        Scoresheet scoresheet = Optional.ofNullable(player.getScoreSheet()).orElseThrow(() -> new MissingScoresheetException(player));

        if(frameIndex != scoresheet.getCurrentFrameIndex()){
            String msg = String.format("The provided frame index does not match the current frame index: %s.", scoresheet.getCurrentFrameIndex());
            throw new BadRequestException(msg);
        }

        Frame currentFrame = scoresheet.getFrames().get(frameIndex);
        if(currentFrame == null){
            currentFrame = Frame.builder()
                    .scoresheet(scoresheet)
                    .frameIndex(frameIndex)
                    .build();
            scoresheet.getFrames().add(currentFrame);
        }

        recordFrameScore(currentFrame,ballIndex,score);
        recalculateScoresheet(scoresheet);

        playerRepository.save(player);
    }

    /**
     * Record the number of pins knocked over for a given throw of a frame
     * @param frame
     * @param ballIndex
     * @param score
     */
    private void recordFrameScore(Frame frame, int ballIndex, int score){
        if(frame.isComplete()){
            String msg = String.format("Exceeded maximum ball throws for this frame index: %s.", frame.getFrameIndex());
            throw new BadRequestException(msg);
        }
        if(score > frame.getRemainingNumberOfPins()){
            String msg = String.format("Attempted to knock over more pins than there are remaining. Only %s pins remain.", frame.getRemainingNumberOfPins());
            throw new BadRequestException(msg);
        }
        switch (ballIndex) {
            case 0:
                frame.setFirstRoll(score);
                break;
            case 1:
                frame.setSecondRoll(score);
                break;
            case 2:
                if(frame.getFrameIndex() == 9) {
                    frame.setThirdRoll(score);
                } else{
                    throw new BadRequestException("Slow down. You're throwing too many balls.");
                }
                break;
            default:
                throw new BadRequestException("Slow down. You're throwing too many balls.");
        }
    }

    /**
     * Calculate a given player's current score
     * @param playerId
     * @return player's current score
     */
    @Override
    public ScoreDto getCurrentScore(Long playerId) {
        int currentScore = 0;
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new ResourceNotFoundException(Player.class,"id",playerId));
        Scoresheet scoresheet = Optional.ofNullable(player.getScoreSheet()).orElseThrow(() -> new MissingScoresheetException(player));
        int currentFrameIndex = scoresheet.getCurrentFrameIndex();

        if(!CollectionUtils.isEmpty(scoresheet.getFrames())){
            currentScore = scoresheet.getFrames().get(currentFrameIndex).getScore();
        }

        return new ScoreDto(currentScore);
    }

    /**
     * Retrieve the scoresheet of a given player
     * @param playerId
     * @return scoresheet of a given player
     */
    @Override
    public ScoresheetDto getScoresheet(Long playerId) {
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new ResourceNotFoundException(Player.class,"id",playerId));
        Scoresheet scoresheet = Optional.ofNullable(player.getScoreSheet()).orElseThrow(() -> new MissingScoresheetException(player));
        return null;
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
            frameScore = getFrameScore(scoresheet.getFrames(),frame.getFrameIndex());
            frame.setScore(frameScore);
            totalScore += frameScore;
        }
        return totalScore;
    }

    /**
     * Calculate the score for a given frame index
     * @param frames
     * @param frameIndex
     * @return frame score
     */
    private int getFrameScore(List<Frame> frames, int frameIndex) {
        Frame frame = frames.get(frameIndex);
        if (frame == null) {
            return 0;
        }
        if (frame.isStrike()) {
            return 10 + strikeBonus(frames,frameIndex);
        }
        if (frame.isSpare()) {
            return 10 + spareBonus(frames,frameIndex);
        }
        return rollValue(frame.getFirstRoll()) + rollValue(frame.getSecondRoll());
    }

    /**
     * Calculate the Spare Bonus for a frame
     * Bonus is based on next frame
     * @param frames
     * @param frameIndex
     * @return frame score with spare bonus calculated
     */
    private int spareBonus(List<Frame> frames, int frameIndex) {
        if (frameIndex == 9) {
            Frame lastFrame = frames.get(frameIndex);
            return rollValue(lastFrame.getThirdRoll());
        }
        Frame nextFrame = frames.get(frameIndex + 1);
        return nextFrame == null ? 0 : rollValue(nextFrame.getFirstRoll());
    }

    /**
     * Calculate the Strike Bonus for a frame
     * Bonus is based on next frame
     * @param frames
     * @param frameIndex
     * @return frame score with strike bonus calculated
     */
    private int strikeBonus(List<Frame> frames, int frameIndex) {
        if (frameIndex == 9) {
            Frame lastFrame = frames.get(frameIndex);
            return rollValue(lastFrame.getSecondRoll()) + rollValue(lastFrame.getThirdRoll());
        }
        Frame nextFrame = frames.get(frameIndex + 1);
        if (nextFrame == null) {
            return 0;
        }
        if (nextFrame.isStrike()) {
            if (frameIndex == 8) {
                return 10 + rollValue(nextFrame.getSecondRoll());
            }
            Frame nextNextFrame = frames.get(frameIndex + 2);
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
