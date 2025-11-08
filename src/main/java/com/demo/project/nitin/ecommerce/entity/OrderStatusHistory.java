package com.demo.project.nitin.ecommerce.entity;

import com.demo.project.nitin.ecommerce.constant.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "order_status_history")
public class OrderStatusHistory extends BaseEntity {

    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "order_product_id")
    private OrderProduct orderProduct;

    @Enumerated(EnumType.STRING)
    private OrderStatus fromStatus;

    @Enumerated(EnumType.STRING)
    private OrderStatus toStatus;

    private String transitionNotesComments;

    private LocalDateTime transitionDate;
}
