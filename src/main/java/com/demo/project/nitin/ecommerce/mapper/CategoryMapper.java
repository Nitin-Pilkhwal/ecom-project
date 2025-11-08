package com.demo.project.nitin.ecommerce.mapper;

import com.demo.project.nitin.ecommerce.dto.request.CategoryMetadataFieldRequestDTO;
import com.demo.project.nitin.ecommerce.dto.request.CategoryRequestDTO;
import com.demo.project.nitin.ecommerce.dto.response.*;
import com.demo.project.nitin.ecommerce.entity.Category;
import com.demo.project.nitin.ecommerce.entity.CategoryMetadataField;
import com.demo.project.nitin.ecommerce.entity.CategoryMetadataFieldValues;
import com.demo.project.nitin.ecommerce.utils.MappingUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "leaf",constant = "true")
    Category toEntity(CategoryRequestDTO dto);

    CategoryMetadataField toEntity(CategoryMetadataFieldRequestDTO dto);

    CategoryMetadataFieldResponseDTO toDTO(CategoryMetadataField categoryMetadataField);

    @Mapping(target = "parentCategory", ignore = true)
    CategoryChainResponseDTO toDTO(Category category);

    @Mapping(target = "category", source = "category")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "lastModifiedAt", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    CategoryMetadataFieldValues toEntity(CategoryMetadataFieldValues categoryMetadataFieldValues,Category category);

    @Mapping(target = "id", source = "values.id.categoryMetadataField")
    @Mapping(target = "fieldName", source = "field.name")
    CategoryMetadataFieldValuesResponseDTO toDTO(CategoryMetadataFieldValues values,CategoryMetadataField field);

    @Mapping(target = "metadataFields", expression = "java(mapMetadataFields(category.getCategoryMetadataFieldValuesList()))")
    CategoryResponseDTO toCustomerCategoryDTO(Category category);

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "metadataFields", source = "metadataFields")
    @Mapping(target = "brands", source = "brands")
    @Mapping(target = "priceRange", source = "price")
    CategoryFiltersResponseDTO toFilterDTO(Category category,
                                     List<CategoryMetadataFieldValuesResponseDTO> metadataFields,
                                     List<String> brands,
                                     PriceResponseDTO price);

    default List<CategoryMetadataFieldValuesResponseDTO> mapMetadataFields(List<CategoryMetadataFieldValues> fieldValues) {
        return MappingUtil.mapMetadataFields(fieldValues);
    }
}
