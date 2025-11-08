package com.demo.project.nitin.ecommerce.controller.customer;

import com.demo.project.nitin.ecommerce.dto.request.AddressDTO;
import com.demo.project.nitin.ecommerce.dto.request.update.UpdateCustomerDTO;
import com.demo.project.nitin.ecommerce.dto.response.ResponseDTO;
import com.demo.project.nitin.ecommerce.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final MessageSource messageSource;

    @GetMapping("/get/profile")
    public ResponseEntity<ResponseDTO> viewProfile(Authentication authentication, Locale locale) {
        ResponseDTO response = customerService.viewProfile(authentication.getName());
        response.setResponseMessage(messageSource.getMessage(response.getResponseMessage(), null, locale));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add/address")
    public ResponseEntity<ResponseDTO> addAddress(
            Authentication authentication,
            @RequestBody @Valid AddressDTO addressDTO,
            Locale locale
    ) {
        ResponseDTO responseDTO = customerService.addAddress(authentication.getName(),addressDTO);
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(), null, locale));
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/remove/address/{addressId}")
    public ResponseEntity<ResponseDTO> removeAddress(
            Authentication authentication,
            @PathVariable String addressId,
            Locale locale
    ) {
        ResponseDTO responseDTO = customerService.removeAddress(authentication.getName(), addressId);
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(), null, locale));
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/get/addresses")
    public ResponseEntity<ResponseDTO> getAddresses(Authentication authentication) {
        ResponseDTO responseDTO = customerService.getAddresses(authentication.getName());
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/update/profile")
    public ResponseEntity<ResponseDTO> updateProfile(
            Authentication authentication,
            @RequestBody @Valid UpdateCustomerDTO dto,
            Locale locale
    ) {
        ResponseDTO responseDTO = customerService.updateProfile(authentication.getName(), dto);
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(), null, locale));
        return ResponseEntity.ok(responseDTO);
    }
}
