package com.demo.project.nitin.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;
import java.util.UUID;


@Getter
@Setter
@Entity
@SQLRestriction("is_deleted = false")
@SQLDelete(sql = "UPDATE products SET is_deleted = true WHERE id = ?")
@Table(
        name = "products",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_product_name_brand_category_seller",
                        columnNames = {"name", "brand", "category_id", "seller_user_id"}
                )
        }
)
public class Product extends BaseEntity{
    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    private boolean isCancellable;

    private boolean isReturnable;

    @Column(nullable = false)
    private String brand;

    private boolean isActive;

    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "seller_user_id")
    private Seller seller;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "product")
    List<ProductReview> productReviews;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    List<ProductVariation> productVariations;
}
