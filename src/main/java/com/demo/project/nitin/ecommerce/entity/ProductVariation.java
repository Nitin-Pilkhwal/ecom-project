package com.demo.project.nitin.ecommerce.entity;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Getter
@Setter
@Entity
@SQLRestriction("is_deleted = false")
@SQLDelete(sql = "UPDATE product_variations SET is_deleted = true WHERE id = ?")
@Table(name = "product_variations")
public class ProductVariation extends BaseEntity{
    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantityAvailable;

    private double price;

    private String primaryImageName;

    private boolean isDeleted;

    private boolean isActive;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata_json", columnDefinition = "JSON")
    private JsonNode metadata;
}
