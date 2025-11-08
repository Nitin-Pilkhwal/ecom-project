package com.demo.project.nitin.ecommerce.entity;

import com.demo.project.nitin.ecommerce.constant.enums.TokenType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String tokenValue;

    private LocalDateTime expiredAt;

    @Enumerated(EnumType.STRING)
    private TokenType type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
