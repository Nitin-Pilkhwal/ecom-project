package com.demo.project.nitin.ecommerce.entity;

import com.demo.project.nitin.ecommerce.constant.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order extends BaseEntity{
    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "customer_user_id")
    private Customer customer;

    @OneToMany(mappedBy = "order")
    private List<OrderProduct> orderProducts;

    private double amountPaid;

    private PaymentMethod paymentMethod;

    private String customerAddressCity;

    private String customerAddressState;

    private String customerAddressCountry;

    private String customerAddressAddressLine;

    private String customerAddressZipcode;

    private String customerAddressLabel;
}
