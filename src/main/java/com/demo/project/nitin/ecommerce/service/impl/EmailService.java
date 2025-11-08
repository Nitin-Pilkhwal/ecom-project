package com.demo.project.nitin.ecommerce.service.impl;

import com.demo.project.nitin.ecommerce.constant.enums.NotificationTemplateType;
import com.demo.project.nitin.ecommerce.entity.NotificationMessage;
import com.demo.project.nitin.ecommerce.exception.exceptions.EmailServiceException;
import com.demo.project.nitin.ecommerce.mapper.NotificationMapper;
import com.demo.project.nitin.ecommerce.repository.NotificationMessageRepo;
import com.demo.project.nitin.ecommerce.service.NotificationService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService implements NotificationService {

    private final JavaMailSender mailSender;
    private final NotificationMapper notificationMapper;
    private final NotificationMessageRepo notificationMessageRepo;

    @Override
    @Async
    public void sendNotification(String to, NotificationTemplateType notificationTemplateType, String messageBody) {
        sendEmail(to,notificationTemplateType.getSubject(), messageBody);
        NotificationMessage notificationMessage = notificationMapper.toMessage(to,notificationTemplateType,messageBody);
        notificationMessageRepo.save(notificationMessage);
    }

    private void sendEmail(String to, String subject, String emailBody) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(emailBody, true);
            mailSender.send(mimeMessage);
            log.info("Sent email to user: {} with subject: {}", to, subject);
        } catch (Exception e) {
            log.error("Failed to send the mail {}", e.getMessage(), e);
            throw new EmailServiceException("Failed to send the mail", e);
        }
    }
}
