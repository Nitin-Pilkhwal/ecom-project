package com.demo.project.nitin.ecommerce.mapper;

import com.demo.project.nitin.ecommerce.constant.enums.NotificationTemplateType;
import com.demo.project.nitin.ecommerce.dto.request.NotificationTemplateRequestDTO;
import com.demo.project.nitin.ecommerce.entity.NotificationMessage;
import com.demo.project.nitin.ecommerce.entity.NotificationTemplate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mapping(target = "notificationTemplateType", source = "type")
    @Mapping(target = "template", source = "template")
    NotificationTemplate toTemplate(NotificationTemplateRequestDTO requestDTO);

    NotificationMessage toMessage(String receiver, NotificationTemplateType notificationTemplateType, String message);
}
