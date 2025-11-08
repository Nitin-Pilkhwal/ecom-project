package com.demo.project.nitin.ecommerce.repository;

import com.demo.project.nitin.ecommerce.constant.enums.Authority;
import com.demo.project.nitin.ecommerce.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepo extends JpaRepository<Role, UUID> {

    Optional<Role> findByAuthority(Authority authority);
}
