package com.demo.project.nitin.ecommerce.entity.composite_key;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@Embeddable
public class ProductReviewId implements Serializable {
    private UUID customer;
    private UUID product;
}