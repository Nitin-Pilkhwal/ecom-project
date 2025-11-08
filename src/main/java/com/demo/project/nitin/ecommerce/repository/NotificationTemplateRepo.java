package com.demo.project.nitin.ecommerce.repository;

import com.demo.project.nitin.ecommerce.constant.enums.NotificationTemplateType;
import com.demo.project.nitin.ecommerce.entity.NotificationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface NotificationTemplateRepo extends JpaRepository<NotificationTemplate, UUID>, JpaSpecificationExecutor<NotificationTemplate> {

    Optional<NotificationTemplate> findByNotificationTemplateTypeAndIsDeletedFalse(NotificationTemplateType notificationTemplateType);
}
