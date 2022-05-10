package com.springboot.bowling.repository;

import com.springboot.bowling.entity.Frame;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Create a repository for {@link Frame} entity to allow us to perform CRUD operations
 */
public interface FrameRepository extends JpaRepository<Frame, Long> {
}
