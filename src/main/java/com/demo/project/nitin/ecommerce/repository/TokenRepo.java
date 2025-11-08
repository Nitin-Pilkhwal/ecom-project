package com.demo.project.nitin.ecommerce.repository;

import com.demo.project.nitin.ecommerce.constant.enums.TokenType;
import com.demo.project.nitin.ecommerce.entity.Token;
import com.demo.project.nitin.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepo extends JpaRepository<Token, UUID> {

    Optional<Token> findByUserAndType(User user, TokenType type);

    Optional<Token> findByTokenValueAndType(String tokenValue, TokenType type);

    @Modifying
    @Transactional
    @Query("DELETE FROM Token t WHERE t.user = :user")
    void deleteAllByUser(User user);

    @Modifying
    @Transactional
    @Query("DELETE FROM Token t WHERE t.expiredAt<:now AND t.type=:type")
    int deleteByExpiryDateBeforeAndType_Refresh(LocalDateTime now, TokenType type);
}
