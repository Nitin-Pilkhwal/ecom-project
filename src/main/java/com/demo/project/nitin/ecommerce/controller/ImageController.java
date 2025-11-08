package com.demo.project.nitin.ecommerce.controller;

import com.demo.project.nitin.ecommerce.constant.enums.ImageType;
import com.demo.project.nitin.ecommerce.dto.response.ResponseDTO;
import com.demo.project.nitin.ecommerce.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Locale;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;
    private final MessageSource messageSource;

    @PostMapping("/upload/profile/image")
    public ResponseEntity<ResponseDTO> uploadUserImage(
            @RequestParam("file") MultipartFile file,
            Authentication authentication,
            Locale locale
    ) {
        ResponseDTO responseDTO = imageService.uploadImage(file, ImageType.USER, authentication.getName());
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(),null, locale));
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping(
            value = "/get/profile/image/{userId}",
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE}
    )
    public byte[] getUserImage(@PathVariable("userId") String userId){
        return imageService.getProfileImage(userId);
    }

    @GetMapping(
            value = "/get/product/variation/image/{imageId}",
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE}
    )
    public byte[] getProductVariationImage(@PathVariable("imageId") String imageId){
        return imageService.getVariationImage(imageId);
    }

    @PreAuthorize("hasRole('ROLE_SELLER')")
    @DeleteMapping("/remove/product/variation/secondary/image/{imageId}")
    public ResponseEntity<ResponseDTO> uploadVariationImage(
            @PathVariable("imageId") String imageId,
            Authentication authentication,
            Locale locale
    ) {
        ResponseDTO responseDTO = imageService.removeVariationImage(imageId, authentication.getName());
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(),null, locale));
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }
}
