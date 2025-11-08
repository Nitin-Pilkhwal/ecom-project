package com.demo.project.nitin.ecommerce.service;

import com.demo.project.nitin.ecommerce.dto.filter.CategoryFilter;
import com.demo.project.nitin.ecommerce.dto.request.AddCategoryMetadataRequestDTO;
import com.demo.project.nitin.ecommerce.dto.request.CategoryMetadataFieldRequestDTO;
import com.demo.project.nitin.ecommerce.dto.request.CategoryRequestDTO;
import com.demo.project.nitin.ecommerce.dto.request.update.UpdateCategoryDTO;
import com.demo.project.nitin.ecommerce.dto.request.update.UpdateCategoryMetadataRequestDTO;
import com.demo.project.nitin.ecommerce.dto.response.ResponseDTO;
import org.springframework.data.domain.Pageable;

public interface AdminCategoryService {

    ResponseDTO createMetadataField(CategoryMetadataFieldRequestDTO dto);

    ResponseDTO getAllMetaFields(String name, Pageable pageable);

    ResponseDTO createCategory(CategoryRequestDTO dto);

    ResponseDTO updateCategory(UpdateCategoryDTO dto);

    ResponseDTO getCategory(String categoryId);

    ResponseDTO getAllCategories(CategoryFilter filterDto, Pageable pageable);

    ResponseDTO addMetadataFieldValue(AddCategoryMetadataRequestDTO addCategoryMetadataRequestDTO);

    ResponseDTO changeMetadataValue(UpdateCategoryMetadataRequestDTO addCategoryMetadataRequestDTO);
}
