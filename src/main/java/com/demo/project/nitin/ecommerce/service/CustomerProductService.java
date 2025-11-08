package com.demo.project.nitin.ecommerce.service;

import com.demo.project.nitin.ecommerce.dto.filter.ProductFilter;
import com.demo.project.nitin.ecommerce.dto.response.ResponseDTO;
import org.springframework.data.domain.Pageable;

public interface CustomerProductService {

    ResponseDTO getProductWithVariations(String productId);

    ResponseDTO getAllProducts(ProductFilter filter, Pageable pageable, String categoryId);

    ResponseDTO getSimilarProducts(String productId, Pageable pageable);
}
