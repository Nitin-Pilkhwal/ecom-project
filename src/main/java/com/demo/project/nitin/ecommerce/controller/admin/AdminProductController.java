package com.demo.project.nitin.ecommerce.controller.admin;

import com.demo.project.nitin.ecommerce.dto.filter.ProductFilter;
import com.demo.project.nitin.ecommerce.dto.response.ResponseDTO;
import com.demo.project.nitin.ecommerce.service.AdminProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final AdminProductService adminProductService;
    private final MessageSource messageSource;

    @GetMapping("/get/product/{productId}")
    ResponseEntity<ResponseDTO> getProduct(@PathVariable String productId,Locale locale) {
        ResponseDTO responseDTO = adminProductService.getProductWithVariations(productId);
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(),null,locale));
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PatchMapping("/activate/Product/{productId}")
    ResponseEntity<ResponseDTO> activateProduct(@PathVariable("productId") String productId, Locale locale) {
        ResponseDTO responseDTO = adminProductService.activateProduct(productId);
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(), null, locale));
        return ResponseEntity.ok(responseDTO);
    }

    @PatchMapping("/deactivate/Product/{productId}")
    ResponseEntity<ResponseDTO> deactivateProduct(@PathVariable("productId") String productId, Locale locale) {
        ResponseDTO responseDTO = adminProductService.deactivateProduct(productId);
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(), null, locale));
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/get/all/products")
    ResponseEntity<ResponseDTO> getAllProducts(
            @ModelAttribute @Valid ProductFilter filter,
            Pageable pageable,
            @RequestParam(value = "sellerId",required = false) String sellerId,
            Locale locale
    ) {
        ResponseDTO responseDTO = adminProductService.getAllProducts(filter,pageable,sellerId);
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(), null, locale));
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }
}
