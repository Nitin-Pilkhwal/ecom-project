package com.demo.project.nitin.ecommerce.dto.request;

import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import com.demo.project.nitin.ecommerce.constant.enums.NotificationTemplateType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationTemplateRequestDTO {

    @NotNull(message = MessageKeys.TEMPLATE_TYPE_REQUIRED)
    private NotificationTemplateType type;

    @NotBlank(message = MessageKeys.TEMPLATE_HTML_BLANK)
    private String template;
}
