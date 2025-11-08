package com.demo.project.nitin.ecommerce.controller;

import com.demo.project.nitin.ecommerce.constant.Constants;
import com.demo.project.nitin.ecommerce.dto.*;
import com.demo.project.nitin.ecommerce.dto.request.*;
import com.demo.project.nitin.ecommerce.dto.request.update.PasswordResetDTO;
import com.demo.project.nitin.ecommerce.dto.response.ResponseDTO;
import com.demo.project.nitin.ecommerce.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final MessageSource messageSource;

    @PostMapping("/register/customer")
    public ResponseEntity<ResponseDTO> registerCustomer(
            @RequestBody @Valid CustomerRequestDTO customerRequestDTO,
            Locale locale
    ) {
        ResponseDTO responseDTO = authService.registerCustomer(customerRequestDTO);
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(), null, locale));
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PostMapping("/register/seller")
    public ResponseEntity<ResponseDTO> registerSeller(
            @RequestBody @Valid SellerRequestDTO sellerRequestDTO,
            Locale locale
    ) {
        ResponseDTO responseDTO = authService.registerSeller(sellerRequestDTO);
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(), null, locale));
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/activate/customer")
    public ResponseEntity<ResponseDTO> activateCustomer(@RequestParam String token, Locale locale) {
        ResponseDTO responseDTO = authService.activateCustomer(token);
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(), null, locale));
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/resend/activation-link")
    public ResponseEntity<ResponseDTO> resendActivationLink(@RequestBody @Valid EmailDTO emailDTO, Locale locale) {
        ResponseDTO responseDTO = authService.resendActivationLink(emailDTO.getEmail());
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(), null, locale));
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> userLogin(@RequestBody @Valid LoginRequestDTO loginRequestDto, Locale locale) {
        ResponseDTO responseDTO = authService.userLogin(loginRequestDto);
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(), null, locale));
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDTO> userLogout(
            @RequestHeader(Constants.Security.AUTHORIZATION_HEADER) String token,
            Locale locale
    ) {
        ResponseDTO responseDTO = authService.userLogout(token);
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(), null, locale));
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/refresh")
    public ResponseEntity<ResponseDTO> getAccessToken(@RequestBody @Valid TokenDTO tokenDTO, Locale locale) {
        ResponseDTO responseDTO = authService.generateNewAccessToken(tokenDTO.getRefreshToken());
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(), null, locale));
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/initiate/forgot-password")
    public ResponseEntity<ResponseDTO> initiateForgotPassword(@RequestBody @Valid EmailDTO emailDTO, Locale locale) {
        ResponseDTO responseDTO = authService.initiateForgotPassword(emailDTO.getEmail());
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(), null, locale));
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/reset-password")
    public ResponseEntity<ResponseDTO> resetPassword(
            @RequestParam String token,
            @RequestBody @Valid PasswordResetDTO passwordResetDTO,
            Locale locale
    ) {
        ResponseDTO responseDTO = authService.resetPassword(token, passwordResetDTO);
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(), null, locale));
        return ResponseEntity.ok(responseDTO);
    }
}
