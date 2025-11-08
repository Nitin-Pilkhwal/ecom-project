package com.demo.project.nitin.ecommerce.entity;

import com.demo.project.nitin.ecommerce.entity.composite_key.CartId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "carts")
public class Cart {

    @EmbeddedId
    private CartId cartId = new CartId();

    @ManyToOne
    @MapsId("customerID")
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @MapsId("productVariationID")
    @JoinColumn(name = "product_variation_id")
    private ProductVariation productVariation;

    private int quantity;

    private boolean isWishListItem;
}