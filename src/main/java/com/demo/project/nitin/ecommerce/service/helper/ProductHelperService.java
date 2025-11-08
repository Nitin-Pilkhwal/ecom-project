package com.demo.project.nitin.ecommerce.service.helper;

import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import com.demo.project.nitin.ecommerce.dto.request.ProductRequestDTO;
import com.demo.project.nitin.ecommerce.dto.request.ProductVariationRequestDTO;
import com.demo.project.nitin.ecommerce.entity.*;
import com.demo.project.nitin.ecommerce.exception.exceptions.DuplicateFieldException;
import com.demo.project.nitin.ecommerce.exception.exceptions.ResourceNotFoundException;
import com.demo.project.nitin.ecommerce.mapper.ProductMapper;
import com.demo.project.nitin.ecommerce.repository.CategoryMetadataFieldValuesRepo;
import com.demo.project.nitin.ecommerce.repository.ProductRepo;
import com.demo.project.nitin.ecommerce.repository.ProductVariationRepo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.demo.project.nitin.ecommerce.utils.CommonUtil.checkDuplicate;
import static com.demo.project.nitin.ecommerce.utils.CommonUtil.toUUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductHelperService {
    private final ProductRepo productRepo;
    private final ProductVariationRepo productVariationRepo;
    private final CategoryMetadataFieldValuesRepo categoryMetadataFieldValuesRepo;
    private final ProductMapper productMapper;
    private final ObjectMapper objectMapper;
    private final FileHelperService fileHelperService;

    public Product findByIdAndSeller(String id, Seller seller) {
        return productRepo.findByIdAndSeller(toUUID(id), seller)
                .orElseThrow(() -> {
                    log.error("Product: {} not found with seller {}", id,seller.getId());
                    return new ResourceNotFoundException(MessageKeys.PRODUCT_NOT_FOUND);
                });
    }

    public Product createProduct(Seller seller, Category category, ProductRequestDTO dto) {
        checkDuplicate(
                productRepo.findByBrandAndNameAndCategoryAndSeller(dto.getBrand(), dto.getName(), category, seller),
                MessageKeys.PRODUCT_ALREADY_EXISTS
        );
        if (!category.isLeaf()) {
            throw new IllegalArgumentException(MessageKeys.NOT_LEAF_CATEGORY);
        }
        Product product = productMapper.toEntity(dto, category, seller);
        return saveProduct(product);
    }

    public Product saveProduct(Product product) {
        return productRepo.save(product);
    }

    public void deleteProduct(Product product) {
        productRepo.delete(product);
    }

    public JsonNode validateMetadata(Product product, Map<String, String> metadata) {
        if (metadata == null || metadata.isEmpty()) {
            throw new IllegalArgumentException("Variation must have at least one metadata field");
        }
        Map<String, Set<String>> allowedData = getAllowedData(product);
        validateMetadataField(product, metadata, allowedData);
        Map<String, String> variationData = new HashMap<>();
        for (Map.Entry<String, Set<String>> entry : allowedData.entrySet()) {
            String key = entry.getKey();
            if (metadata.containsKey(key)) {
                if (allowedData.get(key).contains(metadata.get(entry.getKey()))) {
                    variationData.put(key, metadata.get(entry.getKey()));
                } else {
                    log.error("Metadata field value: {} is not found for product: {}", metadata.get(key), product.getId());
                    throw new IllegalArgumentException(MessageKeys.METADATA_FIELD_VALUE_NOT_FOUND);
                }
            } else {
                variationData.put(key, "NA");
            }
        }
        return checkIsVariationUnique(product, variationData);
    }

    public ProductVariation saveProductVariation(ProductVariationRequestDTO dto, Product product, JsonNode metadata) {
        log.info("Saving variation for product: {}", product.getId());
        ProductVariation variation = productMapper.toVariationEntity(dto, product, metadata);
        variation = productVariationRepo.save(variation);
        String primaryImageName = fileHelperService.addPrimaryImage(
                dto.getPrimaryImage(), product.getId().toString(), variation.getId().toString()
        );
        fileHelperService.addSecondaryImages(
                dto.getSecondaryImages(), product.getId().toString(), variation.getId().toString()
        );
        variation.setPrimaryImageName(primaryImageName);
        return productVariationRepo.save(variation);
    }

    public Product findProductById(String productId) {
        return productRepo.findById(toUUID(productId))
                .orElseThrow(() -> {
                    log.error("Product not found with ID: {}", productId);
                    return new ResourceNotFoundException(MessageKeys.PRODUCT_NOT_FOUND);
                });
    }

    public Product findProductForCustomer(String productId){
        Product product = productRepo.findByIdAndIsActiveTrue(toUUID(productId))
                .orElseThrow(() -> {
                    log.error("Product not found with ID: {}", productId);
                    return new ResourceNotFoundException(MessageKeys.PRODUCT_NOT_FOUND);
                });
        List<ProductVariation> variations = product.getProductVariations()
                .stream().filter(ProductVariation::isActive).toList();
        if(variations.isEmpty()) {
            log.error("Product not found with ID: {}", productId);
            throw new ResourceNotFoundException(MessageKeys.PRODUCT_NOT_FOUND);
        }
        return product;
    }

    private JsonNode checkIsVariationUnique(Product product, Map<String, String> variationData) {
        JsonNode jsonNode = objectMapper.valueToTree(variationData);
        if (productVariationRepo.existsByProductAndMetadata(product.getId(), jsonNode.toString()) > 0) {
            log.error("Variation: {} already exists for product: {}", variationData, product.getId());
            throw new DuplicateFieldException(MessageKeys.PRODUCT_VARIATION_ALREADY_EXISTS);
        }
        return jsonNode;
    }

    private Map<String, Set<String>> getAllowedData(Product product) {
        List<CategoryMetadataFieldValues> allowedValues =
                categoryMetadataFieldValuesRepo.findByCategory(product.getCategory());
        Map<String, Set<String>> allowedData = new HashMap<>();
        for (CategoryMetadataFieldValues fieldValues : allowedValues) {
            CategoryMetadataField categoryMetadataField = fieldValues.getCategoryMetadataField();
            allowedData.put(categoryMetadataField.getName(), fieldValues.getValues());
        }
        return allowedData;
    }

    private void validateMetadataField(Product product, Map<String, String> metadata, Map<String, Set<String>> allowedData) {
        for (Map.Entry<String, String> entry : metadata.entrySet()) {
            if (!allowedData.containsKey(entry.getKey())) {
                log.error("Metadata field: {} is not found for product: {}", entry.getKey(), product.getId());
                throw new IllegalArgumentException(MessageKeys.METADATA_FIELD_NOT_FOUND);
            }
        }
    }

    public ProductVariation findById(String id) {
        return productVariationRepo.findById(toUUID(id))
                .orElseThrow(() -> {
                            log.error("Product not found with ID: {}", id);
                            return new ResourceNotFoundException(MessageKeys.PRODUCT_VARIATION_NOT_FOUND);});
    }

    public ProductVariation findProductVariationByIdAndSeller(String variationId, Seller seller) {
        return productVariationRepo.findByIdAndSeller(toUUID(variationId), seller)
                .orElseThrow(() -> {
                    log.error("Variation: {} not found for seller: {}", variationId, seller.getId());
                    return new ResourceNotFoundException(MessageKeys.PRODUCT_VARIATION_NOT_FOUND);
                });
    }
}
