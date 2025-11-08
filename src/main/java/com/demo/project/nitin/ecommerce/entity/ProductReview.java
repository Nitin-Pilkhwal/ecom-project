package com.demo.project.nitin.ecommerce.entity;

import com.demo.project.nitin.ecommerce.constant.enums.Rating;
import com.demo.project.nitin.ecommerce.entity.composite_key.ProductReviewId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@Entity
@Table(name = "product_reviews")
@SQLRestriction("is_deleted = false")
public class ProductReview extends BaseEntity{
    @EmbeddedId
    private ProductReviewId id = new ProductReviewId();

    @ManyToOne
    @MapsId("customer")
    @JoinColumn(name = "customer_user_id")
    private Customer customer;

    @ManyToOne
    @MapsId("product")
    @JoinColumn(name = "product_id")
    private Product product;

    private String review;

    private boolean isDeleted;

    @Enumerated(EnumType.STRING)
    private Rating rating;
}