package com.demo.project.nitin.ecommerce.controller.seller;

import com.demo.project.nitin.ecommerce.dto.request.update.UpdateSellerDTO;
import com.demo.project.nitin.ecommerce.dto.response.ResponseDTO;
import com.demo.project.nitin.ecommerce.service.SellerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/api/sellers")
@RequiredArgsConstructor
public class SellerController {

    private final SellerService sellerService;
    private final MessageSource messageSource;

    @GetMapping("/get/profile")
    public ResponseEntity<ResponseDTO> viewProfile(Authentication authentication, Locale locale) {
        ResponseDTO response = sellerService.viewProfile(authentication.getName());
        response.setResponseMessage(messageSource.getMessage(response.getResponseMessage(), null, locale));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/profile")
    public ResponseEntity<ResponseDTO> updateProfile(
            Authentication authentication,
            @RequestBody @Valid UpdateSellerDTO dto,
            Locale locale
    ) {
        ResponseDTO responseDTO = sellerService.updateProfile(authentication.getName(), dto);
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(), null, locale));
        return ResponseEntity.ok(responseDTO);
    }

}
