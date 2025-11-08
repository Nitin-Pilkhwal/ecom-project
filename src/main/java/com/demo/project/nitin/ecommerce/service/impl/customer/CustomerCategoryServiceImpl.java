package com.demo.project.nitin.ecommerce.service.impl.customer;

import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import com.demo.project.nitin.ecommerce.dto.response.*;
import com.demo.project.nitin.ecommerce.entity.Category;
import com.demo.project.nitin.ecommerce.entity.CategoryMetadataFieldValues;
import com.demo.project.nitin.ecommerce.mapper.CategoryMapper;
import com.demo.project.nitin.ecommerce.mapper.ResponseMapper;
import com.demo.project.nitin.ecommerce.repository.CategoryMetadataFieldValuesRepo;
import com.demo.project.nitin.ecommerce.repository.CategoryRepo;
import com.demo.project.nitin.ecommerce.repository.ProductRepo;
import com.demo.project.nitin.ecommerce.repository.ProductVariationRepo;
import com.demo.project.nitin.ecommerce.service.CustomerCategoryService;
import com.demo.project.nitin.ecommerce.service.helper.CategoryHelperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.demo.project.nitin.ecommerce.utils.CommonUtil.*;
import static com.demo.project.nitin.ecommerce.utils.MappingUtil.mapMetadataFields;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerCategoryServiceImpl implements CustomerCategoryService {

    private final ResponseMapper responseMapper;
    private final CategoryRepo categoryRepo;
    private final CategoryMetadataFieldValuesRepo metadataFieldValuesRepo;
    private final ProductRepo productRepo;
    private final CategoryHelperService categoryHelperService;
    private final CategoryMapper categoryMapper;
    private final ProductVariationRepo productVariationRepo;

    @Override
    public ResponseDTO getCategories(String categoryId, Pageable pageable) {
        Pageable effectivePageable = getEffectivePageable(pageable);
        Page<Category> categories;
        if (categoryId != null) {
            Category parentCategory = categoryHelperService.findById(categoryId);
            categories = categoryRepo.findByParentCategory(parentCategory, effectivePageable);
        } else {
            categories = categoryRepo.findByParentCategoryIsNull(effectivePageable);
        }
        log.info("Fetched {} categories", categories.getNumberOfElements());
        Page<CategoryResponseDTO> responseDTOPage = categories.map(categoryMapper::toCustomerCategoryDTO);
        return responseMapper.toResponseDto(HttpStatus.OK, MessageKeys.CATEGORY_FETCHED, responseDTOPage);
    }

    @Override
    public ResponseDTO getFilteringDetails(String categoryId) {
        Category category = categoryHelperService.findById(categoryId);
        List<Category> leafCategories = (category.isLeaf()) ?
                List.of(category) :
                categoryRepo.findAllLeafDescendants(toUUID(categoryId));
        List<UUID> leafCategoriesIds = leafCategories.stream().map(Category::getId).toList();
        List<CategoryMetadataFieldValues> metadataFieldValues =
                metadataFieldValuesRepo.findByCategoryIdIn(leafCategoriesIds);
        List<CategoryMetadataFieldValuesResponseDTO> metadataFieldValuesResponseDTOS =
                mapMetadataFields(metadataFieldValues);
        List<String> brands = productRepo.findBrandsByCategory(leafCategoriesIds);
        PriceResponseDTO priceDTO = productVariationRepo.findMinMaxPrice(leafCategoriesIds);
        CategoryFiltersResponseDTO response =
                categoryMapper.toFilterDTO(category, metadataFieldValuesResponseDTOS, brands, priceDTO);
        return responseMapper.toResponseDto(HttpStatus.OK, MessageKeys.CATEGORY_FETCHED, response);
    }
}
