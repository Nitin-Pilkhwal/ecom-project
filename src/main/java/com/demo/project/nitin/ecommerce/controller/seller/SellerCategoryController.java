package com.demo.project.nitin.ecommerce.controller.seller;

import com.demo.project.nitin.ecommerce.dto.response.ResponseDTO;
import com.demo.project.nitin.ecommerce.service.SellerCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/api/sellers/category")
@RequiredArgsConstructor
public class SellerCategoryController {

    private final SellerCategoryService sellerCategoryService;
    private final MessageSource messageSource;

    @GetMapping("/get/all/categories")
    public ResponseEntity<ResponseDTO> getAllCategories(Pageable pageable, Locale locale) {
        ResponseDTO responseDTO = sellerCategoryService.getAllCategories(pageable);
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(), null, locale));
        return ResponseEntity.ok(responseDTO);
    }
}
