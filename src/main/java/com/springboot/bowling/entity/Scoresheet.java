package com.springboot.bowling.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
    @Min(0)
    @Max(9)
    private int currentFrameIndex = 0;

    /**
     * 10 frames listed on this scoresheet
     */
    @OneToMany(mappedBy = "scoresheet", cascade = CascadeType.ALL, orphanRemoval = true)
    @Size(max=10)
    private List<Frame> frames = new ArrayList<>();

    /**
     * Represent this scoresheet as a string of frame scores
     * @return String of frame scores
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();

        Iterator<Frame> it = frames.iterator();
        if(it.hasNext()){
            sb.append(it.next().toString());
            while(it.hasNext()){
                sb.append(" ").append(it.next().toString());
            }
        }

        return sb.toString();
    }
}
