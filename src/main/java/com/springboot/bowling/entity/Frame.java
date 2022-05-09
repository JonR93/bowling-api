package com.springboot.bowling.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.CompareToBuilder;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Represents a frame on a players scoresheet to keep track of the players score
 */

@Data
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
    @Size(max = 9)
    private int frameIndex = 0;

    /**
     * Number of pins the player knocked down on the first roll
     */
    private Integer firstRoll;

    /**
     * Number of pins the player knocked down on the second roll
     */
    private Integer secondRoll;

    /**
     * Number of pins the player knocked down on the third roll
     * Note: only applicable in the 10th frame
     */
    private Integer thirdRoll;

    /**
     * Points scored during this frame
     */
    private int totalPoints = 0;

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
}
