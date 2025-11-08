package com.demo.project.nitin.ecommerce.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CategoryMetadataFieldValuesResponseDTO {
    private String id;
    private String fieldName;
    private Set<String> values;
}