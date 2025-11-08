package com.demo.project.nitin.ecommerce.dto.response;

import com.demo.project.nitin.ecommerce.constant.enums.NotificationTemplateType;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class NotificationTemplateResponseDTO {

    private UUID id;

    private NotificationTemplateType notificationTemplateType;

    private String template;
}
