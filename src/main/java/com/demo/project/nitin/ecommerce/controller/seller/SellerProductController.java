package com.demo.project.nitin.ecommerce.controller.seller;

import com.demo.project.nitin.ecommerce.dto.filter.ProductFilter;
import com.demo.project.nitin.ecommerce.dto.filter.ProductVariationFilter;
import com.demo.project.nitin.ecommerce.dto.request.ProductRequestDTO;
import com.demo.project.nitin.ecommerce.dto.request.ProductVariationRequestDTO;
import com.demo.project.nitin.ecommerce.dto.request.update.UpdateProductDTO;
import com.demo.project.nitin.ecommerce.dto.request.update.UpdateProductVariationDTO;
import com.demo.project.nitin.ecommerce.dto.response.ResponseDTO;
import com.demo.project.nitin.ecommerce.service.SellerProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/api/sellers/products")
@RequiredArgsConstructor
public class SellerProductController {

    private final SellerProductService sellerProductService;
    private final MessageSource messageSource;

    @PostMapping("/add/product")
    ResponseEntity<ResponseDTO> addProduct(
            @RequestBody @Valid ProductRequestDTO productRequestDTO,
            Authentication authentication,
            Locale locale
    ) {
        ResponseDTO responseDTO = sellerProductService.addProduct(productRequestDTO,authentication.getName());
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(), null, locale));
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/get/product/{productId}")
    ResponseEntity<ResponseDTO> getProduct(
            Authentication authentication,
            @PathVariable("productId") String productId,
            Locale locale
    ) {
        ResponseDTO responseDTO = sellerProductService.getProduct(authentication.getName(),productId);
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(),null,locale));
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @GetMapping("/get/all/products")
    ResponseEntity<ResponseDTO> getAllProducts(
            Authentication authentication,
            @ModelAttribute @Valid ProductFilter productFilter,
            Pageable pageable,
            Locale locale
    ){
        ResponseDTO responseDTO = sellerProductService.getAllProducts(authentication.getName(),productFilter,pageable);
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(),null,locale));
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @DeleteMapping("/remove/product/{productId}")
    ResponseEntity<ResponseDTO> deleteProduct(
            @PathVariable("productId") String productId,
            Authentication authentication,
            Locale locale
    ){
        ResponseDTO responseDTO = sellerProductService.deleteProduct(productId,authentication.getName());
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(),null,locale));
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PostMapping(value = "/add/product/variation", produces = {"application/json"})
    public ResponseEntity<ResponseDTO> addProductVariation(
            @ModelAttribute ProductVariationRequestDTO request,
            Authentication authentication,
            Locale locale
    ){
        ResponseDTO responseDTO = sellerProductService.addProductVariation(authentication.getName(),request);
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(),null,locale));
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PutMapping("/change/product/variation/{variationId}")
    public ResponseEntity<ResponseDTO> changeProductVariation(
            @PathVariable("variationId") String variationId,
            @ModelAttribute @Valid UpdateProductVariationDTO dto,
            Authentication authentication,
            Locale locale
    ){
        ResponseDTO responseDTO = sellerProductService.updateProductVariation(variationId,dto,authentication.getName());
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(),null,locale));
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @GetMapping("/get/product/variation/{variationId}")
    public ResponseEntity<ResponseDTO> getProductVariation(@PathVariable("variationId") String variationId, Authentication authentication,Locale locale){
        ResponseDTO responseDTO = sellerProductService.getProductVariation(variationId,authentication.getName());
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(),null,locale));
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @GetMapping("/get/all/product/variation/{productId}")
    public ResponseEntity<ResponseDTO> getAllProductVariation(
            @PathVariable("productId") String productId,
            @ModelAttribute @Valid ProductVariationFilter filter,
            Pageable pageable,
            Authentication authentication,
            Locale locale
    ){
        ResponseDTO responseDTO = sellerProductService.getAllProductVariation(productId,filter,pageable,authentication.getName());
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(),null,locale));
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PutMapping("/change/product/{productId}")
    public ResponseEntity<ResponseDTO> changeProduct(
            @PathVariable("productId") String productId,
            @RequestBody @Valid UpdateProductDTO dto,
            Authentication authentication,
            Locale locale
    ){
        ResponseDTO responseDTO = sellerProductService.updateProduct(dto,productId,authentication.getName());
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(),null,locale));
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }
}
