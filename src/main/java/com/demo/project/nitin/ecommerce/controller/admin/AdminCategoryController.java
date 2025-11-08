package com.demo.project.nitin.ecommerce.controller.admin;

import com.demo.project.nitin.ecommerce.dto.filter.CategoryFilter;
import com.demo.project.nitin.ecommerce.dto.request.AddCategoryMetadataRequestDTO;
import com.demo.project.nitin.ecommerce.dto.request.CategoryMetadataFieldRequestDTO;
import com.demo.project.nitin.ecommerce.dto.request.CategoryRequestDTO;
import com.demo.project.nitin.ecommerce.dto.request.update.UpdateCategoryDTO;
import com.demo.project.nitin.ecommerce.dto.request.update.UpdateCategoryMetadataRequestDTO;
import com.demo.project.nitin.ecommerce.dto.response.ResponseDTO;
import com.demo.project.nitin.ecommerce.service.AdminCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/admin/category")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final AdminCategoryService categoryService;
    private final MessageSource messageSource;

    @PostMapping("/add/metadata/field")
    ResponseEntity<ResponseDTO> addCategory(@RequestBody @Valid CategoryMetadataFieldRequestDTO dto, Locale locale) {
        ResponseDTO responseDTO = categoryService.createMetadataField(dto);
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(), null, locale));
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/getAll/metadata/field")
    ResponseEntity<ResponseDTO> getAllCategory(
            Pageable pageable,@RequestParam(value = "name", required = false) String name,Locale locale
    ) {
        ResponseDTO responseDTO = categoryService.getAllMetaFields(name,pageable);
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(), null, locale));
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PostMapping("/add/category")
    ResponseEntity<ResponseDTO> addCategory(@RequestBody @Valid CategoryRequestDTO dto, Locale locale) {
        ResponseDTO responseDTO = categoryService.createCategory(dto);
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(), null, locale));
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PatchMapping("/change/category")
    ResponseEntity<ResponseDTO> changeCategory(@RequestBody @Valid UpdateCategoryDTO dto, Locale locale) {
        ResponseDTO responseDTO = categoryService.updateCategory(dto);
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(), null, locale));
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @GetMapping("/get/category/{categoryId}")
    ResponseEntity<ResponseDTO> getCategory(@PathVariable("categoryId") String categoryId, Locale locale) {
        ResponseDTO responseDTO = categoryService.getCategory(categoryId);
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(), null, locale));
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @GetMapping("/get/all/categories")
    ResponseEntity<ResponseDTO> getAllCategories(
            @ModelAttribute @Valid CategoryFilter filterDto,
            Pageable pageable,
            Locale locale
    ) {
        ResponseDTO responseDTO = categoryService.getAllCategories(filterDto,pageable);
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(), null, locale));
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PostMapping("/add/metadata/field/value")
    ResponseEntity<ResponseDTO> addMetadataValue(@RequestBody @Valid AddCategoryMetadataRequestDTO dto, Locale locale) {
        ResponseDTO responseDTO = categoryService.addMetadataFieldValue(dto);
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(), null, locale));
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PutMapping("/change/metadata/field/value")
    ResponseEntity<ResponseDTO> changeMetadataValue(@RequestBody @Valid UpdateCategoryMetadataRequestDTO dto, Locale locale){
        ResponseDTO responseDTO = categoryService.changeMetadataValue(dto);
        responseDTO.setResponseMessage(messageSource.getMessage(responseDTO.getResponseMessage(), null, locale));
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }
}
