package com.demo.project.nitin.ecommerce.repository;

import com.demo.project.nitin.ecommerce.dto.response.PriceResponseDTO;
import com.demo.project.nitin.ecommerce.entity.ProductVariation;
import com.demo.project.nitin.ecommerce.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductVariationRepo extends JpaRepository<ProductVariation, UUID>,
        JpaSpecificationExecutor<ProductVariation> {
    @Query("""
        SELECT new com.demo.project.nitin.ecommerce.dto.response.PriceResponseDTO(
            MIN(pv.price),
            MAX(pv.price)
        )
        FROM ProductVariation pv
        JOIN pv.product p
        JOIN p.category c
        WHERE c.id IN :categoryIds
    """)
    PriceResponseDTO findMinMaxPrice(@Param("categoryIds") List<UUID> categoryIds);

    @Query(value = """
        SELECT COUNT(*)
        FROM product_variations v
        WHERE v.product_id = :productId
        AND v.metadata_json = CAST(:metadata AS JSON)
        AND v.is_deleted = false
    """, nativeQuery = true)
    int existsByProductAndMetadata(@Param("productId") UUID productId,
                                       @Param("metadata") String metadata);

    @Query("""
        SELECT pv
        FROM ProductVariation pv
        INNER JOIN Product p
        ON pv.product = p
        WHERE pv.id = :variationId AND p.seller = :seller
    """)
    Optional<ProductVariation> findByIdAndSeller(UUID variationId, Seller seller);

    @Query("""
    SELECT pv
    FROM ProductVariation pv
    JOIN FETCH pv.product p
    WHERE pv.isActive = true
      AND pv.quantityAvailable = 0
    """)
    List<ProductVariation> findOutOfStockVariations();

}
