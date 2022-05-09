package com.springboot.bowling.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * An entity that has a collection of 10 frames that keeps track of the players score
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "scoresheets")
public class Scoresheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Keep track of the current frame being played
     */
    @Size(max=9)
    private int currentFrameIndex = 0;

    /**
     * 10 frames listed on this scoresheet
     */
    @OneToMany(mappedBy = "scoresheet", cascade = CascadeType.ALL, orphanRemoval = true)
    @Size(max=10)
    private List<Frame> frames = new ArrayList<>();

    /**
     * Total score
     * @return total score of all played frames
     */
    @Transient
    public int getTotalScore() {
        int totalScore = 0;
        Collections.sort(frames);
        for(Frame frame: frames){
            totalScore += getFrameScore(frame.getFrameIndex());
        }
        return totalScore;
    }

    /**
     * Calculate the score for a given frame index
     * @param frameIndex
     * @return frame score
     */
    private int getFrameScore(int frameIndex) {
        Frame frame = frames.get(frameIndex);
        if (frame == null) {
            return 0;
        }
        if (frame.isStrike()) {
            return 10 + strikeBonus(frameIndex);
        }
        if (frame.isSpare()) {
            return 10 + spareBonus(frameIndex);
        }
        return rollValue(frame.getFirstRoll()) + rollValue(frame.getSecondRoll());
    }

    /**
     * Calculate the Spare Bonus for a frame
     * @param frameIndex
     * @return frame score with spare bonus calculated
     */
    private int spareBonus(int frameIndex) {
        if (frameIndex == 9) {
            Frame lastFrame = frames.get(frameIndex);
            return rollValue(lastFrame.getThirdRoll());
        }
        Frame nextFrame = frames.get(frameIndex + 1);
        return nextFrame == null ? 0 : rollValue(nextFrame.getFirstRoll());
    }

    /**
     * Calculate the Strike Bonus for a frame
     * @param frameIndex
     * @return frame score with strike bonus calculated
     */
    private int strikeBonus(int frameIndex) {
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
