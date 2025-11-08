package com.demo.project.nitin.ecommerce.entity.composite_key;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
public class CategoryMetadataFieldValuesId implements Serializable{
    private UUID category;
    private UUID categoryMetadataField;


}
