package com.demo.project.nitin.ecommerce.specification;

import com.demo.project.nitin.ecommerce.dto.filter.ProductFilter;
import com.demo.project.nitin.ecommerce.entity.Product;
import com.demo.project.nitin.ecommerce.entity.Seller;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;


import static com.demo.project.nitin.ecommerce.utils.CommonUtil.toUUID;

public class ProductSpecification {

    private ProductSpecification() {}

    public static Specification<Product> build(Seller seller, ProductFilter filter) {
        return Specification.allOf(
                belongsToSeller(seller),
                hasName(filter.getName()),
                hasBrand(filter.getBrand()),
                isCancellable(filter.getIsCancellable()),
                isReturnable(filter.getIsReturnable()),
                belongsToCategory(filter.getCategoryId()),
                isActive(filter.getIsActive()),
                withVariations()
        );
    }

    public static Specification<Product> withVariations() {
        return (root, query, cb) -> {
            if (query != null && query.getResultType() != Long.class) {
                root.fetch("productVariations", JoinType.LEFT);
            }
            return cb.conjunction();
        };
    }

    public static Specification<Product> excludeProduct(String productId) {
        return (root, query, cb) -> cb.notEqual(root.get("id"), toUUID(productId));
    }

    public static Specification<Product> hasSimilarCategoryOrBrand(Product baseProduct) {
        return (root, query, cb) -> cb.and(
                cb.equal(root.get("isActive"), true),
                cb.or(
                        cb.equal(root.get("category"), baseProduct.getCategory()),
                        cb.equal(cb.lower(root.get("brand")), baseProduct.getBrand().toLowerCase())
                )
        );
    }

    public static Specification<Product> buildWithCategory(String categoryId, ProductFilter filter) {
        return Specification.allOf(
                hasName(filter.getName()),
                hasBrand(filter.getBrand()),
                isCancellable(filter.getIsCancellable()),
                isReturnable(filter.getIsReturnable()),
                belongsToCategory(categoryId),
                isActive(filter.getIsActive()),
                withVariations()
        );
    }

    public static Specification<Product> isActive(Boolean isActive) {
        return (root, query, cb) ->
                isActive == null ? null : cb.equal(root.get("isActive"), isActive);
    }

    public static Specification<Product> belongsToSeller(Seller seller) {
        return (root, query, cb) ->
                seller == null ? null : cb.equal(root.get("seller"), seller);
    }

    public static Specification<Product> hasName(String name) {
        return (root, query, cb) ->
                (name == null) ? null : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Product> hasBrand(String brand) {
        return (root, query, cb) ->
                (brand == null) ? null : cb.like(cb.lower(root.get("brand")), "%" + brand.toLowerCase() + "%");
    }

    public static Specification<Product> isCancellable(Boolean isCancellable) {
        return (root, query, cb) ->
                isCancellable == null ? null : cb.equal(root.get("isCancellable"), isCancellable);
    }

    public static Specification<Product> isReturnable(Boolean isReturnable) {
        return (root, query, cb) ->
                isReturnable == null ? null : cb.equal(root.get("isReturnable"), isReturnable);
    }

    public static Specification<Product> belongsToCategory(String categoryId) {
        return (root, query, cb) ->
                categoryId == null ? null : cb.equal(root.get("category").get("id"), toUUID(categoryId));
    }
}