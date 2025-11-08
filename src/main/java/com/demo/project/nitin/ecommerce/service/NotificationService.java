package com.demo.project.nitin.ecommerce.service;

import com.demo.project.nitin.ecommerce.constant.enums.NotificationTemplateType;

public interface NotificationService {

    void sendNotification(String to, NotificationTemplateType notificationTemplateType, String text);
}
