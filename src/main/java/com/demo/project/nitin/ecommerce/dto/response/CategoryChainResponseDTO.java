package com.demo.project.nitin.ecommerce.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CategoryChainResponseDTO {
    private UUID id;
    private String name;

    private boolean isLeaf;

    private ParentCategoryDTO parentCategory;

    private List<CategoryResponseDTO> subCategories;

    private List<CategoryMetadataFieldValuesResponseDTO> metadataFields;
}
