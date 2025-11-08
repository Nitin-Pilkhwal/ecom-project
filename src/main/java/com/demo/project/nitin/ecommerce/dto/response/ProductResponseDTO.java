package com.demo.project.nitin.ecommerce.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDTO {

    private String id;

    private String name;

    private String description;

    private String brand;

    private CategoryResponseDTO category;

    private boolean isCancellable;

    private boolean isReturnable;

    private boolean isActive;
}
