package com.demo.project.nitin.ecommerce.dto.filter;

import com.demo.project.nitin.ecommerce.constant.enums.NotificationTemplateType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationTemplateFilter {
    private NotificationTemplateType notificationTemplateType;
    private Boolean isDeleted;
}
