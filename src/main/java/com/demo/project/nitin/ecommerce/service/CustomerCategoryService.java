package com.demo.project.nitin.ecommerce.service;

import com.demo.project.nitin.ecommerce.dto.response.ResponseDTO;
import org.springframework.data.domain.Pageable;

public interface CustomerCategoryService {

    ResponseDTO getCategories(String categoryId, Pageable pageable);

    ResponseDTO getFilteringDetails(String categoryId);
}
