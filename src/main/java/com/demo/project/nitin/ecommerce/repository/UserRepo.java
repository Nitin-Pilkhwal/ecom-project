package com.demo.project.nitin.ecommerce.repository;

import com.demo.project.nitin.ecommerce.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepo extends JpaRepository<User, UUID> {

    @EntityGraph(attributePaths = {"addresses"})
    Optional<User> findByEmail(String email);
    
    boolean existsByEmail(String email);

    List<User> findAllByPasswordUpdateDateIsNotNullAndPasswordUpdateDateBeforeAndIsExpiredFalse(LocalDate passwordUpdateDateBefore);
}
