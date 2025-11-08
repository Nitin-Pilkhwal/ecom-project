package com.demo.project.nitin.ecommerce.entity;

import com.demo.project.nitin.ecommerce.constant.enums.Authority;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name=  "roles")
public class Role extends BaseEntity {

    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false,unique = true)
    @Enumerated(EnumType.STRING)
    private Authority authority;
}