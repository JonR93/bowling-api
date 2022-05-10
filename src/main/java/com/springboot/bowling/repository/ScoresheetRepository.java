package com.springboot.bowling.repository;

import com.springboot.bowling.entity.Scoresheet;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Create a repository for {@link Scoresheet} entity to allow us to perform CRUD operations
 */
public interface ScoresheetRepository extends JpaRepository<Scoresheet, Long> {
}
