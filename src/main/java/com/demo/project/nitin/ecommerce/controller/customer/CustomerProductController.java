package com.demo.project.nitin.ecommerce.controller.customer;

import com.demo.project.nitin.ecommerce.dto.filter.ProductFilter;
import com.demo.project.nitin.ecommerce.dto.response.ResponseDTO;
import com.demo.project.nitin.ecommerce.service.CustomerProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/api/customers/products")
@RequiredArgsConstructor
public class CustomerProductController {

    private final CustomerProductService customerProductService;
    private final MessageSource messageSource;

    @GetMapping("/get/product/{productId}")
    ResponseEntity<ResponseDTO> getAllProducts(@PathVariable("productId") String productId, Locale locale){
        ResponseDTO responseDTO = customerProductService.getProductWithVariations(productId);
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(),null,locale));
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @GetMapping("/get/all/products")
    ResponseEntity<ResponseDTO> getAllProducts(
            @RequestParam("categoryId") String categoryId,
            @ModelAttribute @Valid ProductFilter filter,
            Pageable pageable,
            Locale locale
    ) {
        ResponseDTO responseDTO = customerProductService.getAllProducts(filter,pageable,categoryId);
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(), null, locale));
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @GetMapping("/get/similar/products/{productId}")
    ResponseEntity<ResponseDTO> getSimilarProducts(
            @PathVariable("productId") String productId,
            Pageable pageable,
            Locale locale
    ){
        ResponseDTO responseDTO = customerProductService.getSimilarProducts(productId,pageable);
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(), null, locale));
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }
}
