package com.demo.project.nitin.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@PrimaryKeyJoinColumn(name = "user_id")
@Entity
@Table(name = "sellers")
public class Seller extends User{
    @Column(nullable = false, unique = true)
    private String companyName;

    @Column(nullable = false)
    private String companyContact;

    @Column(unique = true)
    private String gst;

    @OneToMany(mappedBy = "seller")
    private List<Product> products;
}
