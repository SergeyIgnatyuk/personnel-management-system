package com.repository;

import com.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * JPA repository for {@link com.model.User}
 * Implementation of {@link JpaRepository} interface.
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
