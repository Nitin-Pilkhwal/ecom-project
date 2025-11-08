package com.demo.project.nitin.ecommerce.specification;

import com.demo.project.nitin.ecommerce.dto.filter.ProductVariationFilter;
import com.demo.project.nitin.ecommerce.entity.Product;
import com.demo.project.nitin.ecommerce.entity.ProductVariation;
import org.springframework.data.jpa.domain.Specification;

public class ProductVariationSpecification {

    private ProductVariationSpecification() {}

    public static Specification<ProductVariation> build(ProductVariationFilter filter,Product product) {
        return Specification.allOf(
                minPrice(filter.getMinPrice()),
                maxPrice(filter.getMaxPrice()),
                minQuantity(filter.getMinQuantity()),
                belongsToProduct(product)
        );
    }

    public static Specification<ProductVariation> belongsToProduct(Product product) {
        return (root, query, cb) -> cb.equal(root.get("product"), product);
    }

    public static Specification<ProductVariation> minPrice(Double minPrice) {
        return (root, query, cb) ->
                minPrice == null ? cb.conjunction() : cb.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public static Specification<ProductVariation> maxPrice(Double maxPrice) {
        return (root, query, cb) ->
                maxPrice == null ? cb.conjunction() : cb.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    public static Specification<ProductVariation> minQuantity(Integer minQty) {
        return (root, query, cb) ->
                minQty == null ? cb.conjunction() : cb.greaterThanOrEqualTo(root.get("quantityAvailable"), minQty);
    }
}