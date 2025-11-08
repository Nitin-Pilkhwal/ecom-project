package com.demo.project.nitin.ecommerce.entity;

import com.demo.project.nitin.ecommerce.constant.enums.NotificationTemplateType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class NotificationTemplate extends BaseEntity {

    @Id
    @Column(name = "id", columnDefinition = "BINARY(16)")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationTemplateType notificationTemplateType;

    @Column(columnDefinition = "text",nullable = false)
    private String template;

    private boolean isDeleted;
}
