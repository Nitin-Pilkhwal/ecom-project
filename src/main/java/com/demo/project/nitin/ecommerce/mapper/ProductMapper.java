package com.demo.project.nitin.ecommerce.mapper;

import com.demo.project.nitin.ecommerce.dto.request.ProductRequestDTO;
import com.demo.project.nitin.ecommerce.dto.request.ProductVariationRequestDTO;
import com.demo.project.nitin.ecommerce.dto.request.update.UpdateProductDTO;
import com.demo.project.nitin.ecommerce.dto.request.update.UpdateProductVariationDTO;
import com.demo.project.nitin.ecommerce.dto.response.*;
import com.demo.project.nitin.ecommerce.entity.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "lastModifiedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "metadata",source = "metadata")
    @Mapping(target = "active", expression = "java(true)")
    @Mapping(target = "deleted", expression = "java(false)")
    ProductVariation toVariationEntity(ProductVariationRequestDTO dto, Product product, JsonNode metadata);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "lastModifiedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "cancellable", source = "dto.isCancellable", defaultValue = "false")
    @Mapping(target = "returnable", source = "dto.isReturnable", defaultValue = "false")
    Product toEntity(ProductRequestDTO dto, Category category, Seller seller);

    ProductResponseDTO toProductResponseDTO(Product product);

    @Mapping(
            target = "metadata",
            expression = "java(objectMapper.convertValue(productVariation.getMetadata(), " +
                    "new com.fasterxml.jackson.core.type.TypeReference<java.util.Map<String, String>>() {}))"
    )
    ProductVariationResponseDTO toVariationDTO(ProductVariation productVariation, ObjectMapper objectMapper);

    ProductVariationListResponseDTO toVariationListDTO(ProductVariation productVariation);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "cancellable", source = "dto.isCancellable")
    @Mapping(target = "returnable", source = "dto.isReturnable")
    void updateProductFromDto(UpdateProductDTO dto, @MappingTarget Product entity);

    @Mapping(target = "variationsImages",expression = "java(toListOfPrimaryImages(product.getProductVariations()))")
    ProductListResponseDTO toProductListDTO(Product product);

    @Mapping(target = "active",source = "dto.isActive")
    @Mapping(target = "metadata",ignore = true)
    void updateProductVariationFromDto(UpdateProductVariationDTO dto, @MappingTarget ProductVariation productVariation);

    @Mapping(target = "variations", expression = "java(toVariationListResponseDTOs(product.getProductVariations()))")
    ProductWithVariationResponseDTO toProductWithVariationDTO(Product product);

    default List<String> toListOfPrimaryImages(List<ProductVariation> variations){
        List<String> primaryImages = new ArrayList<>();
        for(ProductVariation variation : variations){
            primaryImages.add(variation.getPrimaryImageName());
        }
        return primaryImages;
    }

    default List<ProductVariationListResponseDTO> toVariationListResponseDTOs(List<ProductVariation> variations){
        List<ProductVariationListResponseDTO> responseDTOs = new ArrayList<>();
        for(ProductVariation variation : variations){
            responseDTOs.add(toVariationListDTO(variation));
        }
        return responseDTOs;
    }
}
