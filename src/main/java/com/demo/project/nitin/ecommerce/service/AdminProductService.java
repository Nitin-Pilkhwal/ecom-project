package com.demo.project.nitin.ecommerce.service;

import com.demo.project.nitin.ecommerce.dto.filter.ProductFilter;
import com.demo.project.nitin.ecommerce.dto.response.ResponseDTO;
import org.springframework.data.domain.Pageable;

public interface AdminProductService {

    ResponseDTO activateProduct(String productId);

    ResponseDTO deactivateProduct(String productId);

    ResponseDTO getProductWithVariations(String productId);

    ResponseDTO getAllProducts(ProductFilter filter, Pageable pageable, String sellerId);
}
