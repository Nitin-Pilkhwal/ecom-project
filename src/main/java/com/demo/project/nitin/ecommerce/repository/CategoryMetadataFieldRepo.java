package com.demo.project.nitin.ecommerce.repository;

import com.demo.project.nitin.ecommerce.entity.CategoryMetadataField;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CategoryMetadataFieldRepo extends JpaRepository<CategoryMetadataField, UUID> {
    Optional<CategoryMetadataField> findByName(String name);

    Page<CategoryMetadataField> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
