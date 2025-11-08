package com.demo.project.nitin.ecommerce.controller;

import com.demo.project.nitin.ecommerce.dto.request.update.PasswordChangeDTO;
import com.demo.project.nitin.ecommerce.dto.request.update.UpdateAddressDTO;
import com.demo.project.nitin.ecommerce.dto.response.ResponseDTO;
import com.demo.project.nitin.ecommerce.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final MessageSource messageSource;

    @PatchMapping("/change/password")
    public ResponseEntity<ResponseDTO> changePassword(
            Authentication authentication,
            @RequestBody @Valid PasswordChangeDTO passwordResetDTO,
            Locale locale
    ) {
        ResponseDTO responseDTO = userService.changePassword(authentication.getName(), passwordResetDTO);
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(), null, locale));
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/change/address/{addressId}")
    public ResponseEntity<ResponseDTO> updateAddress(
            Authentication authentication,
            @PathVariable String addressId,
            @RequestBody @Valid UpdateAddressDTO updateAddressDTO,
            Locale locale
    ) {
        ResponseDTO responseDTO = userService.updateAddress(authentication.getName(),addressId, updateAddressDTO);
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(), null, locale));
        return ResponseEntity.ok(responseDTO);
    }
}
