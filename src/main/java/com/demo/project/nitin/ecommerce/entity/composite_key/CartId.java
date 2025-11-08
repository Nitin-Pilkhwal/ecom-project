package com.demo.project.nitin.ecommerce.entity.composite_key;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
public class CartId implements Serializable {
    private UUID customerID;
    private UUID productVariationID;
}
