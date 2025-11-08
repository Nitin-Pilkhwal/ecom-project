package com.demo.project.nitin.ecommerce.service;

import com.demo.project.nitin.ecommerce.dto.filter.CustomerFilter;
import com.demo.project.nitin.ecommerce.dto.filter.NotificationTemplateFilter;
import com.demo.project.nitin.ecommerce.dto.filter.SellerFilter;
import com.demo.project.nitin.ecommerce.dto.request.NotificationTemplateRequestDTO;
import com.demo.project.nitin.ecommerce.dto.response.ResponseDTO;
import org.springframework.data.domain.Pageable;


public interface AdminService {
    ResponseDTO getAllCustomers(Pageable pageable, CustomerFilter filter);

    ResponseDTO activateUser(String id);

    ResponseDTO deactivateUser(String id);

    ResponseDTO getAllSellers(Pageable pageable, SellerFilter filter);

    ResponseDTO createTemplate(NotificationTemplateRequestDTO notificationTemplateRequestDTO);

    ResponseDTO getNotificationTemplates(NotificationTemplateFilter filter,Pageable pageable);
}
