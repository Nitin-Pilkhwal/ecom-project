package com.demo.project.nitin.ecommerce.specification;

import com.demo.project.nitin.ecommerce.constant.enums.NotificationTemplateType;
import com.demo.project.nitin.ecommerce.dto.filter.NotificationTemplateFilter;
import com.demo.project.nitin.ecommerce.entity.NotificationTemplate;
import org.springframework.data.jpa.domain.Specification;

public class NotificationTemplateSpecification {

    private NotificationTemplateSpecification() {}

    public static Specification<NotificationTemplate> build(NotificationTemplateFilter filter) {
        return Specification.allOf(
                hasNotificationTemplateType(filter.getNotificationTemplateType()),
                isDeleted(filter.getIsDeleted())
        );
    }

    private static Specification<NotificationTemplate> hasNotificationTemplateType(NotificationTemplateType type) {
        return (root, query, cb) ->
                type == null ? cb.conjunction() : cb.equal(root.get("notificationTemplateType"), type);
    }

    private static Specification<NotificationTemplate> isDeleted(Boolean isDeleted) {
        return (root, query, cb) ->
                isDeleted == null ? cb.conjunction() : cb.equal(root.get("isDeleted"), isDeleted);
    }
}
