package com.demo.project.nitin.ecommerce.repository;

import com.demo.project.nitin.ecommerce.entity.Category;
import com.demo.project.nitin.ecommerce.entity.Product;
import com.demo.project.nitin.ecommerce.entity.Seller;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepo extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {

    List<Product> findByCategory(Category category);

    List<Product> findByBrandAndNameAndCategoryAndSeller(String brand,String name, Category category, Seller seller);

    Optional<Product> findByIdAndSeller(UUID id, Seller seller);

    @Query("""
        SELECT DISTINCT p.brand
        FROM Product p
        JOIN p.category c
        WHERE c.id IN :categoryIds
    """)
    List<String> findBrandsByCategory(@Param("categoryIds") List<UUID> categoryIds);

    @Query(value = """
        SELECT p.*
        FROM products p
        INNER JOIN categories c ON p.category_id = c.id
        LEFT JOIN product_variations pv ON p.id = pv.product_id
        WHERE p.id = :productId
    """,nativeQuery = true)
    Optional<Product> getProductWithVariationsAndCategory(@Param("productId") UUID productId);

    @EntityGraph(attributePaths = {"productVariations","category"})
    Optional<Product> findByIdAndIsActiveTrue(UUID id);
}
