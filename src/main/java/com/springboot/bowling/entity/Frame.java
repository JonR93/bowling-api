package com.springboot.bowling.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.CompareToBuilder;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Optional;

/**
 * Represents a frame on a players scoresheet to keep track of the players score
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "frames")
public class Frame implements Comparable<Frame> {
    /**
     * Frame Unique Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Parent scoresheet
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scoresheet_id", nullable = false)
    private Scoresheet scoresheet;

    /**
     * An index to note which frame this is on the scoresheet
     */
    @Min(0)
    @Max(9)
    private int frameIndex = 0;

    /**
     * Number of pins the player knocked down on the first roll
     */
    @Min(0)
    @Max(10)
    private Integer firstRoll;

    /**
     * Number of pins the player knocked down on the second roll
     */
    @Min(0)
    @Max(10)
    private Integer secondRoll;

    /**
     * Number of pins the player knocked down on the third roll
     * Note: only applicable in the 10th frame
     */
    @Min(0)
    @Max(10)
    private Integer thirdRoll;

    /**
     * The score for this frame
     * Note: can change based on spare/strike bonuses
     */
    private Integer score = 0;

    /**
     * Is this frame a strike?
     * @return true if 10 pins were knocked over in the first roll
     */
    @Transient
    public boolean isStrike(){
        return firstRoll!=null && firstRoll == 10;
    }

    /**
     * Is this frame a spare?
     * @return true if the first roll was not a strike,
     * and the sum of the 1st and 2nd roll are 10 pins
     */
    @Transient
    public boolean isSpare(){
        return firstRoll!=null
                && firstRoll != 10
                && secondRoll!=null
                && Integer.sum(firstRoll, secondRoll) == 10;
    }

    /**
     * Determine what the current ball index of this frame is.
     * @return
     * 0 = not rolled at all
     * 1 = rolled once but did not score a strike in frames(1-9) or scored a strike in frame 10
     * 2 = rolled twice and on the last frame because a strike or spare was scored
     * null = all applicable rolls have been recorded. No more rolls this frame.
     */
    @Transient
    public Integer getCurrentBallIndex(){
        if(firstRoll==null){
            return 0;
        }
        if(secondRoll==null && ((!isStrike() && frameIndex<9) || (frameIndex==9 && isStrike()))){
            return 1;
        }
        if(thirdRoll==null && frameIndex==9 && (isStrike() || isSpare())){
            return 2;
        }
        // you have no more turns on this frame
        return null;
    }

    /**
     * Calculate the number of pins remaining based on previous rolls
     * @return number of pins remaining
     */
    @Transient
    public int getRemainingNumberOfPins(){
        if(frameIndex < 9) {
            // On frames 1-9 you could potentially knock over 10 pins in 2 turns
            return 10
                    - Optional.ofNullable(firstRoll).orElse(0)
                    - Optional.ofNullable(secondRoll).orElse(0);
        } else if(frameIndex == 9) {
            // On frame 10 you could potentially knock over 30 pins in 3 turns
            return 30
                    - Optional.ofNullable(firstRoll).orElse(0)
                    - Optional.ofNullable(secondRoll).orElse(0)
                    - Optional.ofNullable(thirdRoll).orElse(0);
        } else {
            // Invalid frame index. No pins to knock down
            return 0;
        }
    }

    /**
     * Is this frame complete?
     * @return true if the player has completed all their allowed rolls for this frame
     *
     * Frames 1 through 9 are allowed a maximum of 2 rolls.
     * Frame 10 is allowed a maximum of 3 rolls.
     *
     * During frame 10 if the player rolls a strike in their first role they are allowed 2 more rolls.
     * During frame 10 if the player rolls a spare in their first 2 rolls they are allowed a 3rd roll.
     */
    @Transient
    public boolean isComplete(){
        if(firstRoll==null){
            // haven't rolled at all yet
            return false;
        }
        if(frameIndex < 9){
            return isStrike() || secondRoll != null;
        } else{
            // special case for last frame
            if(isStrike() || isSpare()){
                return thirdRoll !=null;
            } else{
                return secondRoll !=null;
            }
        }
    }

    /**
     * Compare frames by frame index
     * @param other Frame
     * @return
     * 0 if the frameIndex is equal to the other frameIndex.
     * < 0 if the frameIndex is less than the other frameIndex.
     * > 0 if the frameIndex is greater than the other frameIndex.
     */
    @Override
    public int compareTo(Frame other){
        if(other == null){
            return 1;
        }
        return new CompareToBuilder()
                .append(this.frameIndex, other.frameIndex)
                .toComparison();
    }

    /**
     * String representation of a frame
     * @return frame represented as 1 to 3 characters,
     * like so:
     *          '-' 0 pins knocked down
     *          '1' to '9' Number of pins knocked down
     *          '/' Spare
     *          'X' Strike
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        if(isStrike()){
            sb.append("X");
            if(frameIndex==9){
                // Check if we need to include bonus rolls on last frame
                if(Integer.valueOf(10).equals(secondRoll)){
                    sb.append("X"); // bonus strike
                    if(Integer.valueOf(10).equals(thirdRoll)){
                        sb.append("X"); // second bonus strike
                    }
                } else if(secondRoll!=null && thirdRoll != null && Integer.sum(secondRoll, thirdRoll) == 10){
                    sb.append(secondRoll.equals(0)?"-":secondRoll).append("/"); // bonus spare
                } else {
                    sb.append(Optional.ofNullable(secondRoll).orElse(0).equals(0)?"-":secondRoll);
                    sb.append(Optional.ofNullable(thirdRoll).orElse(0).equals(0)?"-":thirdRoll);
                }
            }
        } else if(isSpare()){
            sb.append(firstRoll.equals(0)?"-":firstRoll).append("/");
            if(frameIndex==9){
                // Check if we need to include bonus rolls on last frame
                if(Integer.valueOf(10).equals(thirdRoll)){
                    sb.append("X"); // bonus strike
                } else{
                    sb.append(Optional.ofNullable(thirdRoll).orElse(0).equals(0)?"-":thirdRoll); // bonus roll
                }
            }
        } else{
            sb.append(Optional.ofNullable(firstRoll).orElse(0).equals(0)?"-":firstRoll);
            sb.append(Optional.ofNullable(secondRoll).orElse(0).equals(0)?"-":secondRoll);
        }
        return sb.toString();
    }
}
