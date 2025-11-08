package com.demo.project.nitin.ecommerce.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductWithVariationResponseDTO {

    private String id;

    private String name;

    private String description;

    private String brand;

    private CategoryResponseDTO category;

    private List<ProductVariationListResponseDTO> variations;

    private boolean isCancellable;

    private boolean isReturnable;

    private boolean isActive;
}
