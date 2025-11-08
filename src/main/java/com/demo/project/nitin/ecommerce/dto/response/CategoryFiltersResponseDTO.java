package com.demo.project.nitin.ecommerce.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CategoryFiltersResponseDTO {
    private UUID categoryId;
    private String categoryName;
    private List<CategoryMetadataFieldValuesResponseDTO> metadataFields;
    private List<String> brands;
    private PriceResponseDTO priceRange;
}
