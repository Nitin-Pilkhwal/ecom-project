package com.demo.project.nitin.ecommerce.service.impl.admin;

import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import com.demo.project.nitin.ecommerce.dto.filter.ProductFilter;
import com.demo.project.nitin.ecommerce.dto.response.ProductListResponseDTO;
import com.demo.project.nitin.ecommerce.dto.response.ProductWithVariationResponseDTO;
import com.demo.project.nitin.ecommerce.dto.response.ResponseDTO;
import com.demo.project.nitin.ecommerce.entity.Product;
import com.demo.project.nitin.ecommerce.entity.Seller;
import com.demo.project.nitin.ecommerce.exception.exceptions.ResourceNotFoundException;
import com.demo.project.nitin.ecommerce.mapper.ProductMapper;
import com.demo.project.nitin.ecommerce.mapper.ResponseMapper;
import com.demo.project.nitin.ecommerce.repository.ProductRepo;
import com.demo.project.nitin.ecommerce.service.AdminProductService;
import com.demo.project.nitin.ecommerce.service.helper.FileHelperService;
import com.demo.project.nitin.ecommerce.service.helper.NotificationHelperService;
import com.demo.project.nitin.ecommerce.service.helper.ProductHelperService;
import com.demo.project.nitin.ecommerce.service.helper.UserHelperService;
import com.demo.project.nitin.ecommerce.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.demo.project.nitin.ecommerce.utils.CommonUtil.getEffectivePageable;
import static com.demo.project.nitin.ecommerce.utils.CommonUtil.toUUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminProductServiceImpl implements AdminProductService {

    private final ProductHelperService productHelperService;
    private final UserHelperService userHelperService;
    private final ProductMapper productMapper;
    private final ResponseMapper responseMapper;
    private final ProductRepo productRepo;
    private final NotificationHelperService notificationHelperService;
    private final FileHelperService fileHelperService;

    @Override
    public ResponseDTO activateProduct(String productId) {
        Product product = productHelperService.findProductById(productId);
        if(product.isActive()){
            log.error("Product with Id: {} is already active.",product.getId());
            throw new IllegalArgumentException(MessageKeys.PRODUCT_ALREADY_ACTIVE);
        }
        product.setActive(true);
        productRepo.save(product);
        notificationHelperService.sendProductStatusUpdateNotification(product.getSeller(),product,true);
        return responseMapper.toResponseDto(HttpStatus.OK,MessageKeys.PRODUCT_ACTIVATED,null);
    }

    @Override
    public ResponseDTO deactivateProduct(String productId) {
        Product product = productHelperService.findProductById(productId);
        if(!product.isActive()){
            log.error("Product with Id: {} is already inactive.",product.getId());
            throw new IllegalArgumentException(MessageKeys.PRODUCT_ALREADY_INACTIVE);
        }
        product.setActive(false);
        productRepo.save(product);
        notificationHelperService.sendProductStatusUpdateNotification(product.getSeller(),product,false);
        return responseMapper.toResponseDto(HttpStatus.OK,MessageKeys.PRODUCT_DEACTIVATED,null);
    }

    @Override
    public ResponseDTO getProductWithVariations(String productId) {
        Product product = productRepo.getProductWithVariationsAndCategory(toUUID(productId))
                .orElseThrow(() -> {
                    log.error("Product not found with ID: {}", productId);
                    return new ResourceNotFoundException(MessageKeys.PRODUCT_NOT_FOUND);
                });
        ProductWithVariationResponseDTO listResponseDTO = productMapper.toProductWithVariationDTO(product);
        fileHelperService.fillSecondaryImages(productId, listResponseDTO);
        return responseMapper.toResponseDto(HttpStatus.OK,MessageKeys.PRODUCT_FETCHED,listResponseDTO);
    }

    @Override
    public ResponseDTO getAllProducts(ProductFilter filter, Pageable pageable, String sellerId) {
        Seller seller = (sellerId == null)? null : userHelperService.findSellerById(sellerId);
        Pageable effectivePageable = getEffectivePageable(pageable);
        Page<Product> productPage = productRepo.findAll(
                ProductSpecification.build(seller,filter),
                effectivePageable);
        Page<ProductListResponseDTO> responseDTOPage = productPage.map(
                (entity) -> {
                    ProductListResponseDTO newDto = productMapper.toProductListDTO(entity);
                    newDto.setSellerId(entity.getSeller().getId().toString());
                    return newDto;
                }
        );
        return responseMapper.toResponseDto(HttpStatus.OK,MessageKeys.PRODUCT_FETCHED,responseDTOPage);
    }

}
