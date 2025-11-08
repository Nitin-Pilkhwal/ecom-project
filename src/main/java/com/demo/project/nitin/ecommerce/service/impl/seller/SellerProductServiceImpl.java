package com.demo.project.nitin.ecommerce.service.impl.seller;

import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import com.demo.project.nitin.ecommerce.dto.filter.ProductFilter;
import com.demo.project.nitin.ecommerce.dto.filter.ProductVariationFilter;
import com.demo.project.nitin.ecommerce.dto.request.ProductRequestDTO;
import com.demo.project.nitin.ecommerce.dto.request.ProductVariationRequestDTO;
import com.demo.project.nitin.ecommerce.dto.request.update.UpdateProductDTO;
import com.demo.project.nitin.ecommerce.dto.request.update.UpdateProductVariationDTO;
import com.demo.project.nitin.ecommerce.dto.response.ProductResponseDTO;
import com.demo.project.nitin.ecommerce.dto.response.ProductVariationListResponseDTO;
import com.demo.project.nitin.ecommerce.dto.response.ProductVariationResponseDTO;
import com.demo.project.nitin.ecommerce.dto.response.ResponseDTO;
import com.demo.project.nitin.ecommerce.entity.Category;
import com.demo.project.nitin.ecommerce.entity.Product;
import com.demo.project.nitin.ecommerce.entity.ProductVariation;
import com.demo.project.nitin.ecommerce.entity.Seller;
import com.demo.project.nitin.ecommerce.mapper.ProductMapper;
import com.demo.project.nitin.ecommerce.mapper.ResponseMapper;
import com.demo.project.nitin.ecommerce.repository.ProductRepo;
import com.demo.project.nitin.ecommerce.repository.ProductVariationRepo;
import com.demo.project.nitin.ecommerce.service.SellerProductService;
import com.demo.project.nitin.ecommerce.service.helper.*;
import com.demo.project.nitin.ecommerce.specification.ProductSpecification;
import com.demo.project.nitin.ecommerce.specification.ProductVariationSpecification;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static com.demo.project.nitin.ecommerce.utils.CommonUtil.*;
import static com.demo.project.nitin.ecommerce.utils.MappingUtil.mapMetadataFields;

@Service
@Slf4j
@RequiredArgsConstructor
public class SellerProductServiceImpl implements SellerProductService {

    private final ProductHelperService productHelperService;
    private final UserHelperService userHelperService;
    private final CategoryHelperService categoryHelperService;
    private final ObjectMapper objectMapper;
    private final ProductMapper productMapper;
    private final ResponseMapper responseMapper;
    private final ProductRepo productRepo;
    private final ProductVariationRepo productVariationRepo;
    private final NotificationHelperService notificationHelperService;
    private final FileHelperService fileHelperService;

    @Override
    public ResponseDTO addProduct(ProductRequestDTO dto, String sellerUserName) {
        Seller seller = userHelperService.findSellerByEmail(sellerUserName);
        Category category = categoryHelperService.findById(dto.getCategoryId());
        log.info("Adding product '{}' for seller '{}' in category '{}'",
                dto.getName(), seller.getEmail(), category.getName());
        Product product = productHelperService.createProduct(seller, category, dto);
        notificationHelperService.sendNewProductListedNotification(seller, product);
        return responseMapper.toResponseDto(HttpStatus.CREATED, MessageKeys.PRODUCT_CREATED, product.getId());
    }

    @Override
    public ResponseDTO getProduct(String userName, String productId) {
        Seller seller = userHelperService.findSellerByEmail(userName);
        Product product = productHelperService.findByIdAndSeller(productId, seller);
        log.debug("Fetched product with productId: {} for seller: {}",product.getName(), seller.getEmail());
        ProductResponseDTO productResponseDTO = productMapper.toProductResponseDTO(product);
        productResponseDTO.getCategory()
                .setMetadataFields(mapMetadataFields(product.getCategory().getCategoryMetadataFieldValuesList()));
        return responseMapper.toResponseDto(HttpStatus.OK, MessageKeys.PRODUCT_FETCHED, productResponseDTO);
    }

    @Override
    public ResponseDTO getAllProducts(String userName, ProductFilter filter, Pageable pageable) {
        Seller seller = userHelperService.findSellerByEmail(userName);
        Pageable effectivePageable = getEffectivePageable(pageable);
        Page<Product> productPage = productRepo.findAll(
                ProductSpecification.build(seller,filter),
                effectivePageable);
        log.debug("Fetched {} products for seller: {}",productPage.getNumberOfElements(), seller.getEmail());
        Page<ProductResponseDTO> responseDTOPage = productPage.map(productMapper::toProductResponseDTO);
        return responseMapper.toResponseDto(HttpStatus.OK, MessageKeys.PRODUCT_FETCHED, responseDTOPage);
    }

    @Override
    public ResponseDTO deleteProduct(String productId, String userName) {
        Seller seller = userHelperService.findSellerByEmail(userName);
        Product product = productHelperService.findByIdAndSeller(productId, seller);
        log.info("Deleting product '{}' for seller '{}'", productId, seller.getEmail());
        productHelperService.deleteProduct(product);
        return responseMapper.toResponseDto(HttpStatus.OK, MessageKeys.PRODUCT_DELETED, null);
    }

    @Override
    public ResponseDTO addProductVariation(String userName,ProductVariationRequestDTO dto) {
        Seller seller = userHelperService.findSellerByEmail(userName);
        Product product = productHelperService.findByIdAndSeller(dto.getProductId(),seller);
        if(!product.isActive()){
            log.error("Attempted to add product variation to inactive product with Id: {}",product.getId());
            throw new IllegalArgumentException(MessageKeys.PRODUCT_NOT_ACTIVE);
        }
        JsonNode metaData = productHelperService.validateMetadata(product,dto.getMetadata());
        ProductVariation productVariation = productHelperService.saveProductVariation(dto,product,metaData);
        return responseMapper.toResponseDto(HttpStatus.CREATED,MessageKeys.PRODUCT_VARIATION_CREATED,productVariation.getId());
    }

    @Override
    public ResponseDTO getProductVariation(String variationId, String userName) {
        Seller seller = userHelperService.findSellerByEmail(userName);
        ProductVariation productVariation = productHelperService.findProductVariationByIdAndSeller(variationId,seller);
        ProductVariationResponseDTO responseDTO = productMapper.toVariationDTO(productVariation,objectMapper);
        return responseMapper.toResponseDto(HttpStatus.OK,MessageKeys.PRODUCT_VARIATION_FETCHED,responseDTO);
    }

    @Override
    public ResponseDTO getAllProductVariation(
            String productId, ProductVariationFilter filter,Pageable pageable, String userName
    ) {
        Pageable effectivePageable = getEffectivePageable(pageable);
        Seller seller = userHelperService.findSellerByEmail(userName);
        Product product = productHelperService.findByIdAndSeller(productId,seller);
        Page<ProductVariation> variations = productVariationRepo.findAll(
                ProductVariationSpecification.build(filter,product),
                effectivePageable
        );
        Page<ProductVariationListResponseDTO> variationListDTOS = variations.map(productMapper::toVariationListDTO);
        variationListDTOS.forEach(dto ->
                fileHelperService.fillSecondaryImagesInProductVariationListResponse(productId,dto));
        return responseMapper.toResponseDto(HttpStatus.OK,MessageKeys.PRODUCT_VARIATION_FETCHED,variationListDTOS);
    }

    @Override
    public ResponseDTO updateProduct(UpdateProductDTO dto, String productId, String userName) {
        Seller seller = userHelperService.findSellerByEmail(userName);
        Product product = productHelperService.findByIdAndSeller(productId,seller);
        if(dto.getName() != null) {
            checkDuplicate(
                    productRepo.findByBrandAndNameAndCategoryAndSeller(
                            product.getBrand(), dto.getName(), product.getCategory(), seller),
                    MessageKeys.PRODUCT_ALREADY_EXISTS
            );
        }
        productMapper.updateProductFromDto(dto,product);
        ProductResponseDTO responseDTO = productMapper.toProductResponseDTO(productRepo.save(product));
        return responseMapper.toResponseDto(HttpStatus.OK,MessageKeys.PRODUCT_UPDATED,responseDTO);
    }

    @Override
    public ResponseDTO updateProductVariation(String variationId, UpdateProductVariationDTO dto, String userName) {
        Seller seller = userHelperService.findSellerByEmail(userName);
        ProductVariation productVariation = productHelperService.findProductVariationByIdAndSeller(variationId,seller);
        Product product = productVariation.getProduct();
        if(!product.isActive()){
            log.error("Attempted to update product variation of an inactive product with Id: {}",product.getId());
            throw new IllegalArgumentException(MessageKeys.PRODUCT_NOT_ACTIVE);
        }
        if(!matchMetadata(productVariation.getMetadata(),dto.getMetadata())){
            JsonNode metaData = productHelperService.validateMetadata(product,dto.getMetadata());
            productVariation.setMetadata(metaData);
        }
        fileHelperService.addPrimaryImage(
                dto.getPrimaryImage(),product.getId().toString(),productVariation.getId().toString()
        );
        fileHelperService.addSecondaryImages(
                dto.getSecondaryImages(),product.getId().toString(),productVariation.getId().toString()
        );
        productMapper.updateProductVariationFromDto(dto,productVariation);
        productVariationRepo.save(productVariation);
        ProductVariationResponseDTO responseDTO = productMapper.toVariationDTO(productVariation,objectMapper);
        return responseMapper.toResponseDto(HttpStatus.CREATED,MessageKeys.PRODUCT_VARIATION_UPDATED,responseDTO);
    }
}
