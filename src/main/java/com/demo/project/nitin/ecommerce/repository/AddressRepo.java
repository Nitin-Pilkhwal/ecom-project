package com.demo.project.nitin.ecommerce.repository;

import com.demo.project.nitin.ecommerce.entity.Address;
import com.demo.project.nitin.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AddressRepo extends JpaRepository<Address, UUID> {
    
    Optional<Address> findByIdAndUser(UUID id, User user);

    List<Address> findByUserAndIsDeletedFalse(User user);
}
