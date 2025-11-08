package com.demo.project.nitin.ecommerce.repository;

import com.demo.project.nitin.ecommerce.entity.Category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepo extends JpaRepository<Category, UUID>, JpaSpecificationExecutor<Category> {

    List<Category> findByParentCategoryAndName(Category parentCategory, String name);

    @Query(value = """
        WITH RECURSIVE CategoryHierarchy AS (
            SELECT a.id, a.name, a.parent_category_id
            FROM categories a
            WHERE a.id = :categoryId
            UNION ALL
            SELECT b.id, b.name, b.parent_category_id
            FROM categories b
            INNER JOIN CategoryHierarchy ch ON b.id = ch.parent_category_id
        )
        SELECT CASE WHEN COUNT(1) > 0 THEN true ELSE false END as result
        FROM CategoryHierarchy
        WHERE name = :name
        """, nativeQuery = true)
    long findByNameInCategoryHierarchy(@Param("categoryId") UUID categoryId,@Param("name") String name);

    @Query(value = """
    WITH RECURSIVE category_hierarchy AS (
        SELECT c.id, c.parent_category_id
        FROM categories c
        WHERE c.id = :categoryId
        UNION ALL
        SELECT child.id, child.parent_category_id
        FROM categories child
        INNER JOIN category_hierarchy ch ON child.parent_category_id = ch.id
    )
    SELECT *
    FROM categories c
    WHERE c.is_leaf = true 
      AND c.id IN (SELECT id FROM category_hierarchy WHERE id != :categoryId)
    """, nativeQuery = true)
    List<Category> findAllLeafDescendants(@Param("categoryId") UUID categoryId);

    @Query(value = """
            WITH RECURSIVE category_hierarchy AS (
        SELECT c.id, c.parent_category_id, c.name
        FROM categories c
        WHERE c.id = :categoryId
        UNION ALL
        SELECT child.id, child.parent_category_id, child.name
        FROM categories child
                 INNER JOIN category_hierarchy ch ON child.parent_category_id = ch.id
    )
    SELECT CASE WHEN COUNT(1) > 0 THEN true ELSE false END as result
    FROM category_hierarchy
    WHERE LOWER(name) = LOWER(:categoryName);
    """, nativeQuery = true)
    long findSubCategoriesWithName(@Param("categoryId") UUID categoryId, @Param("categoryName") String categoryName);

    Optional<Category> findByNameAndParentCategoryIsNull(String name);

    @EntityGraph(attributePaths = {"categoryMetadataFieldValuesList"})
    Page<Category> findByIsLeafTrue(Pageable pageable);

    @EntityGraph(attributePaths = {"categoryMetadataFieldValuesList"})
    Page<Category> findByParentCategory(Category parentCategory, Pageable pageable);

    @EntityGraph(attributePaths = {"categoryMetadataFieldValuesList"})
    Page<Category> findByParentCategoryIsNull(Pageable pageable);
}
