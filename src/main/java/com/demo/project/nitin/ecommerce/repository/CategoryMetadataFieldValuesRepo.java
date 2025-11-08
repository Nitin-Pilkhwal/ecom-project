package com.demo.project.nitin.ecommerce.repository;

import com.demo.project.nitin.ecommerce.entity.Category;
import com.demo.project.nitin.ecommerce.entity.CategoryMetadataFieldValues;
import com.demo.project.nitin.ecommerce.entity.composite_key.CategoryMetadataFieldValuesId;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CategoryMetadataFieldValuesRepo extends JpaRepository<CategoryMetadataFieldValues, CategoryMetadataFieldValuesId> {

    @EntityGraph(attributePaths = {"categoryMetadataField"})
    List<CategoryMetadataFieldValues> findByCategory(Category category);

    @Query(value = """
        SELECT *
        FROM category_metadata_field_values cmfv
        WHERE cmfv.category_id IN (:categoryIds)
    """, nativeQuery = true)
    List<CategoryMetadataFieldValues> findByCategoryIdIn(@Param("categoryIds") List<UUID> categoryIds);
}
