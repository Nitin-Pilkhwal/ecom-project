package com.demo.project.nitin.ecommerce.specification;

import com.demo.project.nitin.ecommerce.dto.filter.CategoryFilter;
import com.demo.project.nitin.ecommerce.entity.Category;
import org.springframework.data.jpa.domain.Specification;

public class CategorySpecification {

    private CategorySpecification() {}

    public static Specification<Category> build(CategoryFilter filter) {
        return Specification.allOf(
                hasName(filter.getName()),
                hasParentId(filter.getParentId()),
                isLeaf(filter.getLeaf())
        );
    }

    private static Specification<Category> hasName(String name) {
        return (root, query, cb) ->
                name == null ? cb.conjunction() :
                        cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    private static Specification<Category> hasParentId(String parentId) {
        return (root, query, cb) ->
                parentId == null ? cb.conjunction() :
                        cb.equal(root.get("parent").get("id"), parentId);
    }

    private static Specification<Category> isLeaf(Boolean leaf) {
        return (root, query, cb) ->
                leaf == null ? cb.conjunction() : cb.equal(root.get("isLeaf"), leaf);
    }
}