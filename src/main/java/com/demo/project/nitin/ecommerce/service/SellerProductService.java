package com.demo.project.nitin.ecommerce.service;

import com.demo.project.nitin.ecommerce.dto.filter.ProductFilter;
import com.demo.project.nitin.ecommerce.dto.filter.ProductVariationFilter;
import com.demo.project.nitin.ecommerce.dto.request.ProductRequestDTO;
import com.demo.project.nitin.ecommerce.dto.request.ProductVariationRequestDTO;
import com.demo.project.nitin.ecommerce.dto.request.update.UpdateProductDTO;
import com.demo.project.nitin.ecommerce.dto.request.update.UpdateProductVariationDTO;
import com.demo.project.nitin.ecommerce.dto.response.ResponseDTO;
import org.springframework.data.domain.Pageable;

public interface SellerProductService {

    ResponseDTO addProduct(ProductRequestDTO productRequestDTO, String sellerUserName);

    ResponseDTO getProduct(String userName, String productId);

    ResponseDTO getAllProducts(String userName, ProductFilter filter, Pageable pageable);

    ResponseDTO deleteProduct(String productId, String userName);

    ResponseDTO addProductVariation(String userName,ProductVariationRequestDTO request);

    ResponseDTO getProductVariation(String variationId, String userName);

    ResponseDTO getAllProductVariation(String productId, ProductVariationFilter filter, Pageable pageable, String userName);

    ResponseDTO updateProduct(UpdateProductDTO dto, String productId, String userName);

    ResponseDTO updateProductVariation(String variationId,UpdateProductVariationDTO dto, String name);
}
