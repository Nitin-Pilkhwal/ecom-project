package com.demo.project.nitin.ecommerce.service.impl.customer;

import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import com.demo.project.nitin.ecommerce.dto.filter.ProductFilter;
import com.demo.project.nitin.ecommerce.dto.response.ProductListResponseDTO;
import com.demo.project.nitin.ecommerce.dto.response.ProductWithVariationResponseDTO;
import com.demo.project.nitin.ecommerce.dto.response.ResponseDTO;
import com.demo.project.nitin.ecommerce.entity.Product;
import com.demo.project.nitin.ecommerce.entity.ProductVariation;
import com.demo.project.nitin.ecommerce.mapper.ProductMapper;
import com.demo.project.nitin.ecommerce.mapper.ResponseMapper;
import com.demo.project.nitin.ecommerce.repository.ProductRepo;
import com.demo.project.nitin.ecommerce.service.CustomerProductService;
import com.demo.project.nitin.ecommerce.service.helper.CategoryHelperService;
import com.demo.project.nitin.ecommerce.service.helper.FileHelperService;
import com.demo.project.nitin.ecommerce.service.helper.ProductHelperService;
import com.demo.project.nitin.ecommerce.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.demo.project.nitin.ecommerce.utils.CommonUtil.getEffectivePageable;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerProductServiceImpl implements CustomerProductService {

    private final ProductHelperService productHelperService;
    private final CategoryHelperService categoryHelperService;
    private final FileHelperService fileHelperService;
    private final ProductMapper productMapper;
    private final ResponseMapper responseMapper;
    private final ProductRepo productRepo;

    @Override
    public ResponseDTO getProductWithVariations(String productId) {
        Product product = productHelperService.findProductForCustomer(productId);
        ProductWithVariationResponseDTO listResponseDTO = productMapper.toProductWithVariationDTO(product);
        fileHelperService.fillSecondaryImages(productId, listResponseDTO);
        return responseMapper.toResponseDto(HttpStatus.OK,MessageKeys.PRODUCT_FETCHED,listResponseDTO);
    }

    @Override
    public ResponseDTO getAllProducts(ProductFilter filter, Pageable pageable, String categoryId) {
        Pageable effectivePageable = getEffectivePageable(pageable);
        filter.setIsActive(true);
        categoryHelperService.findById(categoryId);
        Page<Product> productPage = productRepo.findAll(
                ProductSpecification.buildWithCategory(categoryId,filter),
                effectivePageable);
        List<ProductListResponseDTO> filteredList = productPage.getContent().stream()
                .filter(product -> product.getProductVariations()
                        .stream()
                        .anyMatch(ProductVariation::isActive))
                .map(productMapper::toProductListDTO)
                .toList();
        Page<ProductListResponseDTO> responseDTOPage =
                new PageImpl<>(filteredList, effectivePageable, filteredList.size());
        return responseMapper.toResponseDto(HttpStatus.OK,MessageKeys.PRODUCT_FETCHED,responseDTOPage);
    }

    @Override
    public ResponseDTO getSimilarProducts(String productId, Pageable pageable) {
        Product product = productHelperService.findProductForCustomer(productId);
        Pageable effectivePageable = getEffectivePageable(pageable);
        Page<Product> productPage = productRepo.findAll(
                Specification.allOf(
                        ProductSpecification.excludeProduct(productId),
                        ProductSpecification.hasSimilarCategoryOrBrand(product)
                ),
                effectivePageable
        );
        Page<ProductListResponseDTO> responseDTOPage = productPage.map(productMapper::toProductListDTO);
        return responseMapper.toResponseDto(HttpStatus.OK,MessageKeys.PRODUCT_FETCHED,responseDTOPage);
    }
}
