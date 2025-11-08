package com.demo.project.nitin.ecommerce.controller.admin;

import com.demo.project.nitin.ecommerce.dto.filter.CustomerFilter;
import com.demo.project.nitin.ecommerce.dto.filter.NotificationTemplateFilter;
import com.demo.project.nitin.ecommerce.dto.filter.SellerFilter;
import com.demo.project.nitin.ecommerce.dto.request.NotificationTemplateRequestDTO;
import com.demo.project.nitin.ecommerce.dto.response.ResponseDTO;
import com.demo.project.nitin.ecommerce.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final MessageSource messageSource;

    @GetMapping("/getAll/customers")
    public ResponseEntity<ResponseDTO> getAllCustomers(
            @ModelAttribute @Valid CustomerFilter filter,
            Pageable pageable,
            Locale locale
    ) {
        ResponseDTO responseDTO = adminService.getAllCustomers(pageable,filter);
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(), null, locale));
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/getAll/sellers")
    public ResponseEntity<ResponseDTO> getAllSellers(
            @ModelAttribute @Valid SellerFilter filter,
            Pageable pageable,
            Locale locale
    ) {
        ResponseDTO responseDTO = adminService.getAllSellers(pageable,filter);
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(), null, locale));
        return ResponseEntity.ok(responseDTO);
    }

    @PatchMapping("/activate/user/{id}")
    public ResponseEntity<ResponseDTO> activateUser(@PathVariable String id,Locale locale){
        ResponseDTO responseDTO = adminService.activateUser(id);
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(), null, locale));
        return ResponseEntity.ok(responseDTO);
    }

    @PatchMapping("/deactivate/user/{id}")
    public ResponseEntity<ResponseDTO> deActivateUser(@PathVariable String id,Locale locale){
        ResponseDTO responseDTO = adminService.deactivateUser(id);
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(), null, locale));
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/add/notification/template")
    public ResponseEntity<ResponseDTO> addNotificationTemplate(
            @RequestBody @Valid NotificationTemplateRequestDTO dto,
            Locale locale
    ){
        ResponseDTO responseDTO = adminService.createTemplate(dto);
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(), null, locale));
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/get/all/notification/template")
    public ResponseEntity<ResponseDTO> getNotificationTemplate(
            @ModelAttribute NotificationTemplateFilter filter,
            Pageable pageable,
            Locale locale
    ){
        ResponseDTO responseDTO = adminService.getNotificationTemplates(filter,pageable);
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(), null, locale));
        return ResponseEntity.ok(responseDTO);
    }
}
