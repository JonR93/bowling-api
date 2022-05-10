package com.springboot.bowling.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Represents a Player registered to play
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "players", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name"})
})
public class Player {
    /**
     * Player Unique Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Player Name
     */
    @Column(nullable = false)
    private String name;

    /**
     * Player's scoresheet for the current game being played
     */
    @OneToOne
    @JoinColumn(name = "scoresheet_id")
    private Scoresheet scoreSheet;

    public String toString(){
        return name;
    }
}
