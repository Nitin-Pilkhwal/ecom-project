package com.demo.project.nitin.ecommerce.dto.response;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ProductVariationListResponseDTO {

    private UUID id;

    private String primaryImageName;

    private List<String> secondaryImages;

    private int quantityAvailable;

    private double price;

    private boolean isActive;

    private JsonNode metadata;
}
