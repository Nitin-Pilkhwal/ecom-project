package com.demo.project.nitin.ecommerce.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class ProductListResponseDTO {

    private String id;

    private String name;

    private String description;

    private String brand;

    private CategoryResponseDTO category;

    private List<String> variationsImages;

    private String sellerId;

    private boolean isDeleted;

    private boolean isCancellable;

    private boolean isReturnable;

    private boolean isActive;
}
