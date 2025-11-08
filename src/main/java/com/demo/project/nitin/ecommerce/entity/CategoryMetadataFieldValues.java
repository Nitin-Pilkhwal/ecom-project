package com.demo.project.nitin.ecommerce.entity;

import com.demo.project.nitin.ecommerce.entity.composite_key.CategoryMetadataFieldValuesId;
import com.demo.project.nitin.ecommerce.utils.annotations.StringSetConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name=  "category_metadata_field_values")
public class CategoryMetadataFieldValues extends BaseEntity{

    @EmbeddedId
    private CategoryMetadataFieldValuesId id = new CategoryMetadataFieldValuesId();

    @ManyToOne
    @MapsId("categoryMetadataField")
    @JoinColumn(name = "category_metadata_field_id")
    private CategoryMetadataField categoryMetadataField;

    @ManyToOne
    @MapsId("category")
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "field_values")
    @Convert(converter = StringSetConverter.class)
    Set<String> values = new HashSet<>();
}
