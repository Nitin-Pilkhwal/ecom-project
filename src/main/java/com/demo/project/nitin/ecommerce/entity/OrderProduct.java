package com.demo.project.nitin.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "order_products")
public class OrderProduct extends BaseEntity{
    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private int quantity;

    private double price;

    @ManyToOne
    @JoinColumn(name = "product_variation_id")
    private ProductVariation productVariation;
}
