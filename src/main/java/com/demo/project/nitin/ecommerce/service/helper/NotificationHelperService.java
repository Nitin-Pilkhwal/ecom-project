package com.demo.project.nitin.ecommerce.service.helper;

import com.demo.project.nitin.ecommerce.configuration.ApplicationConfig;
import com.demo.project.nitin.ecommerce.constant.Constants;
import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import com.demo.project.nitin.ecommerce.constant.enums.NotificationTemplateType;
import com.demo.project.nitin.ecommerce.constant.enums.TokenType;
import com.demo.project.nitin.ecommerce.dto.request.NotificationTemplateRequestDTO;
import com.demo.project.nitin.ecommerce.entity.NotificationTemplate;
import com.demo.project.nitin.ecommerce.entity.Product;
import com.demo.project.nitin.ecommerce.entity.Seller;
import com.demo.project.nitin.ecommerce.entity.User;
import com.demo.project.nitin.ecommerce.exception.exceptions.ResourceNotFoundException;
import com.demo.project.nitin.ecommerce.mapper.NotificationMapper;
import com.demo.project.nitin.ecommerce.repository.NotificationTemplateRepo;
import com.demo.project.nitin.ecommerce.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.demo.project.nitin.ecommerce.utils.CommonUtil.fillValues;

@Service
@RequiredArgsConstructor
public class NotificationHelperService {
    private final NotificationService notificationService;
    private final NotificationTemplateRepo notificationTemplateRepo;
    private final NotificationMapper notificationMapper;
    private final TokenHelperService tokenHelperService;
    private final ApplicationConfig applicationConfig;

    public NotificationTemplate createTemplate(NotificationTemplateRequestDTO dto){
        Optional<NotificationTemplate> optionalTemplate = notificationTemplateRepo
                .findByNotificationTemplateTypeAndIsDeletedFalse(dto.getType());
        if (optionalTemplate.isPresent()) {
            NotificationTemplate existingTemplate = optionalTemplate.get();
            existingTemplate.setDeleted(true);
            notificationTemplateRepo.save(existingTemplate);
        }
        NotificationTemplate newTemplate = notificationMapper.toTemplate(dto);
        return notificationTemplateRepo.save(newTemplate);
    }

    public void sendActivationNotification(User user) {
        String token = tokenHelperService.createTokenEntity(user, TokenType.ACTIVATION).getTokenValue();
        String verificationLink = Constants.API.ACTIVATION_URL + token;
        NotificationTemplate notificationTemplate = getTemplateByType(NotificationTemplateType.ACCOUNT_VERIFICATION);
        String messageBody = fillValues(notificationTemplate.getTemplate(),user.getFirstName(),verificationLink);
        notificationService.sendNotification(user.getEmail(),
                NotificationTemplateType.ACCOUNT_VERIFICATION,
                messageBody);
    }

    public void sendPasswordResetNotification(User user) {
        String token = tokenHelperService.createTokenEntity(user, TokenType.FORGOT_PASSWORD).getTokenValue();
        String resetPasswordLink = Constants.API.PASSWORD_RESET_URL + token;
        NotificationTemplate notificationTemplate = getTemplateByType(NotificationTemplateType.PASSWORD_RESET);
        String messageBody = fillValues(notificationTemplate.getTemplate(),user.getFirstName(),resetPasswordLink);
        notificationService.sendNotification(user.getEmail(),
                NotificationTemplateType.PASSWORD_RESET,
                messageBody);
    }

    public void sendProductStatusUpdateNotification(User user, Product product,boolean isActive) {
        NotificationTemplate notificationTemplate = getTemplateByType(NotificationTemplateType.PRODUCT_STATUS_UPDATE);
        String messageBody;
        if(isActive){
            messageBody = fillValues(
                notificationTemplate.getTemplate(),user.getFirstName(),product.getName(),"green","Activated","green"
            );
        }else{
            messageBody = fillValues(
                notificationTemplate.getTemplate(),user.getFirstName(),product.getName(),"red","Deactivated","red"
            );
        }
        notificationService.sendNotification(user.getEmail(),
                NotificationTemplateType.PRODUCT_STATUS_UPDATE,
                messageBody);
    }

    public void sendNewProductListedNotification(User user,Product product) {
        String adminEmail = applicationConfig.getAdminProperties().getEmail();
        NotificationTemplate notificationTemplate = getTemplateByType(NotificationTemplateType.SELLER_PRODUCT_LISTED);
        String messageBody = fillValues(
                notificationTemplate.getTemplate(),user.getFirstName(),product.getName(),product.getCategory().getName()
        );
        notificationService.sendNotification(adminEmail,
                NotificationTemplateType.SELLER_PRODUCT_LISTED,
                messageBody);
    }

    public void sendAccountCreatedNotification(User user) {
        NotificationTemplate notificationTemplate = getTemplateByType(NotificationTemplateType.WAITING_FOR_APPROVAL);
        String messageBody = fillValues(notificationTemplate.getTemplate(),user.getFirstName());
        notificationService.sendNotification(user.getEmail(),
                NotificationTemplateType.WAITING_FOR_APPROVAL,
                messageBody);
    }

    public void sendAccountLockedNotification(User user) {
        NotificationTemplate notificationTemplate = getTemplateByType(NotificationTemplateType.ACCOUNT_LOCKED);
        String messageBody = fillValues(notificationTemplate.getTemplate(),user.getFirstName());
        notificationService.sendNotification(user.getEmail(),
                NotificationTemplateType.ACCOUNT_LOCKED,
                messageBody);
    }

    public void sendAccountActivatedNotification(User user){
        NotificationTemplate notificationTemplate = getTemplateByType(NotificationTemplateType.ACCOUNT_ACTIVATED);
        String messageBody = fillValues(notificationTemplate.getTemplate(),user.getFirstName());
        notificationService.sendNotification(user.getEmail(),
                NotificationTemplateType.ACCOUNT_ACTIVATED,
                messageBody);
    }

    public void sendPasswordUpdatedNotification(User user) {
        NotificationTemplate notificationTemplate = getTemplateByType(NotificationTemplateType.PASSWORD_UPDATED);
        String messageBody = fillValues(notificationTemplate.getTemplate(),user.getFirstName());
        notificationService.sendNotification(user.getEmail(),
                NotificationTemplateType.PASSWORD_UPDATED,
                messageBody);
    }

    public void sendDeactivationNotification(User user) {
        NotificationTemplate notificationTemplate = getTemplateByType(NotificationTemplateType.ACCOUNT_DEACTIVATED);
        String messageBody = fillValues(notificationTemplate.getTemplate(),user.getFirstName());
        notificationService.sendNotification(user.getEmail(),
                NotificationTemplateType.ACCOUNT_DEACTIVATED,
                messageBody);
    }

    public void sendPasswordExpiryMail(User user) {
        NotificationTemplate notificationTemplate = getTemplateByType(NotificationTemplateType.ACCOUNT_EXPIRED);
        String forgotPasswordLink = Constants.API.INITIATE_FORGOT_PASSWORD;
        String messageBody = fillValues(notificationTemplate.getTemplate(),user.getFirstName(),forgotPasswordLink);
        notificationService.sendNotification(user.getEmail(),
                NotificationTemplateType.ACCOUNT_EXPIRED,
                messageBody);
    }

    public void sendOutOfStockNotification(Seller seller,String products) {
        NotificationTemplate notificationTemplate = getTemplateByType(NotificationTemplateType.OUT_OF_STOCK);
        String messageBody = fillValues(notificationTemplate.getTemplate(),seller.getFirstName(),products);
        notificationService.sendNotification(seller.getEmail(),
                NotificationTemplateType.OUT_OF_STOCK,
                messageBody);
    }

    private NotificationTemplate getTemplateByType(NotificationTemplateType type) {
        return notificationTemplateRepo
                .findByNotificationTemplateTypeAndIsDeletedFalse(type)
                .orElseThrow(() -> new ResourceNotFoundException(MessageKeys.TEMPLATE_NOT_FOUND));
    }
}
