package com.demo.project.nitin.ecommerce.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class ProductVariationResponseDTO {

    private UUID id;

    private ProductResponseDTO product;

    private String primaryImageName;

    private int quantityAvailable;

    private double price;

    private boolean isActive;

    private Map<String, String> metadata;
}
