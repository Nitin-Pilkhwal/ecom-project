package com.demo.project.nitin.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(
        name = "categories",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "parent_category_id"})
)
public class Category extends BaseEntity {

    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id")
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Category> subCategories;

    private boolean isLeaf;

    @OneToMany(mappedBy = "category")
    private List<CategoryMetadataFieldValues> categoryMetadataFieldValuesList;
}
