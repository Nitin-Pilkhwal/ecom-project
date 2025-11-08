package com.demo.project.nitin.ecommerce.controller.customer;

import com.demo.project.nitin.ecommerce.dto.response.ResponseDTO;
import com.demo.project.nitin.ecommerce.service.CustomerCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/api/customers/category")
@RequiredArgsConstructor
public class CustomerCategoryController {

    private final CustomerCategoryService customerCategoryService;
    private final MessageSource messageSource;

    @GetMapping("/get/categories")
    public ResponseEntity<ResponseDTO> getAllCategories(
            @RequestParam(required = false,name = "categoryId") String categoryId,
            Pageable pageable,
            Locale locale
    ) {
        ResponseDTO responseDTO = customerCategoryService.getCategories(categoryId,pageable);
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(), null, locale));
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/get/filtering/details/{categoryId}")
    public ResponseEntity<ResponseDTO> getFilteringDetails(@PathVariable("categoryId") String categoryId, Locale locale) {
        ResponseDTO responseDTO = customerCategoryService.getFilteringDetails(categoryId);
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(), null, locale));
        return ResponseEntity.ok(responseDTO);
    }
}
