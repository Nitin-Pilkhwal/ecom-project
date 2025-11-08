package com.demo.project.nitin.ecommerce.service.impl.admin;

import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import com.demo.project.nitin.ecommerce.constant.enums.Authority;
import com.demo.project.nitin.ecommerce.dto.filter.CustomerFilter;
import com.demo.project.nitin.ecommerce.dto.filter.NotificationTemplateFilter;
import com.demo.project.nitin.ecommerce.dto.filter.SellerFilter;
import com.demo.project.nitin.ecommerce.dto.request.NotificationTemplateRequestDTO;
import com.demo.project.nitin.ecommerce.dto.response.*;
import com.demo.project.nitin.ecommerce.entity.Customer;
import com.demo.project.nitin.ecommerce.entity.NotificationTemplate;
import com.demo.project.nitin.ecommerce.entity.Seller;
import com.demo.project.nitin.ecommerce.entity.User;
import com.demo.project.nitin.ecommerce.mapper.ResponseMapper;
import com.demo.project.nitin.ecommerce.mapper.UserMapper;
import com.demo.project.nitin.ecommerce.repository.CustomerRepo;
import com.demo.project.nitin.ecommerce.repository.NotificationTemplateRepo;
import com.demo.project.nitin.ecommerce.repository.SellerRepo;
import com.demo.project.nitin.ecommerce.service.AdminService;
import com.demo.project.nitin.ecommerce.service.helper.NotificationHelperService;
import com.demo.project.nitin.ecommerce.service.helper.UserHelperService;
import com.demo.project.nitin.ecommerce.specification.CustomerSpecification;
import com.demo.project.nitin.ecommerce.specification.NotificationTemplateSpecification;
import com.demo.project.nitin.ecommerce.specification.SellerSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import static com.demo.project.nitin.ecommerce.utils.CommonUtil.getEffectivePageable;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final ResponseMapper responseMapper;
    private final UserHelperService userHelperService;
    private final NotificationHelperService notificationHelperService;
    private final SellerRepo sellerRepo;
    private final CustomerRepo customerRepo;
    private final UserMapper userMapper;
    private final NotificationTemplateRepo notificationTemplateRepo;

    @Override
    public ResponseDTO getAllCustomers(Pageable pageable, CustomerFilter filter) {
        Pageable effectivePageable = getEffectivePageable(pageable);
        Page<Customer> pageResult = customerRepo.findAll(
                CustomerSpecification.build(filter),
                effectivePageable
        );
        Page<CustomerProfileDTO> dtoPage = pageResult.map(userMapper::toProfileDto);
        dtoPage.forEach(dto -> dto.setImage(
                userHelperService.profileImage(dto.getId().toString())
        ));
        return responseMapper.toResponseDto(HttpStatus.OK, MessageKeys.CUSTOMER_FETCHED,dtoPage);
    }

    @Override
    public ResponseDTO getAllSellers(Pageable pageable, SellerFilter filter) {
        Pageable effectivePageable = getEffectivePageable(pageable);
        Page<Seller> pageResult = sellerRepo.findAll(
                SellerSpecification.build(filter),
                effectivePageable
        );
        Page<SellerProfileDTO> dtoPage = pageResult.map(userMapper::toProfileDto);
        dtoPage.forEach(dto -> dto.setImage(
                userHelperService.profileImage(dto.getId().toString())
        ));
        return responseMapper.toResponseDto(HttpStatus.OK, MessageKeys.SELLER_FETCHED,dtoPage);
    }

    @Override
    public ResponseDTO createTemplate(NotificationTemplateRequestDTO dto) {
        log.info("Added new template for type {}",dto.getType());
        NotificationTemplate notificationTemplate = notificationHelperService.createTemplate(dto);
        return responseMapper.toResponseDto(HttpStatus.CREATED,MessageKeys.TEMPLATE_CREATED,notificationTemplate.getId());
    }

    @Override
    public ResponseDTO getNotificationTemplates(NotificationTemplateFilter filter, Pageable pageable) {
        Pageable effectivePageable = getEffectivePageable(pageable);
        Page<NotificationTemplate> notificationTemplates = notificationTemplateRepo.findAll(
                NotificationTemplateSpecification.build(filter),
                effectivePageable
        );
        return responseMapper.toResponseDto(HttpStatus.OK, MessageKeys.TEMPLATE_FETCHED,notificationTemplates);
    }

    @Override
    public ResponseDTO activateUser(String id) {
        User user = userHelperService.findById(id);
        userHelperService.activateUser(user);
        notificationHelperService.sendAccountActivatedNotification(user);
        return responseMapper.toResponseDto(HttpStatus.OK, MessageKeys.USER_ACTIVATED,null);
    }

    @Override
    public ResponseDTO deactivateUser(String id) {
        User user = userHelperService.findById(id);
        user.getRoles().forEach(role -> {
            if (role.getAuthority() == Authority.ADMIN) {
                throw new IllegalArgumentException(MessageKeys.CANNOT_DEACTIVATE_ADMIN);
            }
        });
        userHelperService.deactivateUser(user);
        notificationHelperService.sendDeactivationNotification(user);
        return responseMapper.toResponseDto(HttpStatus.OK, MessageKeys.USER_DEACTIVATED,null);
    }
}
