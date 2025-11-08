package com.demo.project.nitin.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@PrimaryKeyJoinColumn(name = "user_id")
@Entity
@Table(name= "customers")
public class Customer extends User{
    @Column(nullable = false)
    private String contact;

    @OneToMany(mappedBy = "customer")
    private List<ProductReview> productReviews;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;
}
