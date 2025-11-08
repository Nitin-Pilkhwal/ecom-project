package com.demo.project.nitin.ecommerce.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ParentCategoryDTO {
    private UUID id;
    private String name;
    private ParentCategoryDTO parentCategory;
}
