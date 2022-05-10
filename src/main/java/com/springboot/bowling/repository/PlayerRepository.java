package com.springboot.bowling.repository;

import com.springboot.bowling.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Create a repository for {@link Player} entity to allow us to perform CRUD operations
 */
public interface PlayerRepository extends JpaRepository<Player, UUID> {
    Optional<Player> findByName(String name);
}
