package com.demo.project.nitin.ecommerce.service.impl.admin;

import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import com.demo.project.nitin.ecommerce.dto.filter.CategoryFilter;
import com.demo.project.nitin.ecommerce.dto.request.AddCategoryMetadataRequestDTO;
import com.demo.project.nitin.ecommerce.dto.request.CategoryMetadataFieldRequestDTO;
import com.demo.project.nitin.ecommerce.dto.request.CategoryRequestDTO;
import com.demo.project.nitin.ecommerce.dto.request.FieldValueDTO;
import com.demo.project.nitin.ecommerce.dto.request.update.UpdateCategoryDTO;
import com.demo.project.nitin.ecommerce.dto.request.update.UpdateCategoryMetadataRequestDTO;
import com.demo.project.nitin.ecommerce.dto.response.*;
import com.demo.project.nitin.ecommerce.entity.Category;
import com.demo.project.nitin.ecommerce.entity.CategoryMetadataField;
import com.demo.project.nitin.ecommerce.entity.CategoryMetadataFieldValues;
import com.demo.project.nitin.ecommerce.entity.composite_key.CategoryMetadataFieldValuesId;
import com.demo.project.nitin.ecommerce.exception.exceptions.ResourceNotFoundException;
import com.demo.project.nitin.ecommerce.mapper.CategoryMapper;
import com.demo.project.nitin.ecommerce.mapper.ResponseMapper;
import com.demo.project.nitin.ecommerce.repository.CategoryMetadataFieldRepo;
import com.demo.project.nitin.ecommerce.repository.CategoryMetadataFieldValuesRepo;
import com.demo.project.nitin.ecommerce.repository.CategoryRepo;
import com.demo.project.nitin.ecommerce.service.AdminCategoryService;
import com.demo.project.nitin.ecommerce.service.helper.CategoryHelperService;
import com.demo.project.nitin.ecommerce.specification.CategorySpecification;
import com.demo.project.nitin.ecommerce.utils.MappingUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.demo.project.nitin.ecommerce.utils.CommonUtil.getEffectivePageable;
import static com.demo.project.nitin.ecommerce.utils.CommonUtil.toUUID;
import static com.demo.project.nitin.ecommerce.utils.MappingUtil.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminCategoryServiceImpl implements AdminCategoryService {

    private final CategoryHelperService categoryHelperService;
    private final ResponseMapper responseMapper;
    private final CategoryMapper categoryMapper;
    private final CategoryMetadataFieldRepo fieldRepo;
    private final CategoryRepo categoryRepo;
    private final CategoryMetadataFieldValuesRepo valuesRepo;

    @Override
    public ResponseDTO createMetadataField(CategoryMetadataFieldRequestDTO dto) {
        CategoryMetadataField categoryMetadataField = categoryHelperService.saveCategoryMetadataField(dto);
        return responseMapper.toResponseDto(
                HttpStatus.CREATED,
                MessageKeys.METADATA_FIELD_ADDED,
                categoryMapper.toDTO(categoryMetadataField));
    }

    @Override
    public ResponseDTO getAllMetaFields(String name, Pageable pageable) {
        Pageable effectivePageable = getEffectivePageable(pageable);
        Page<CategoryMetadataField> page;
        if(name!=null && !name.isEmpty()) {
            page = fieldRepo.findByNameContainingIgnoreCase(name, effectivePageable);
        }else{
            page = fieldRepo.findAll(effectivePageable);
        }
        Page<CategoryMetadataFieldResponseDTO> pageDto = page.map(categoryMapper::toDTO);
        return responseMapper.toResponseDto(HttpStatus.OK,MessageKeys.METADATA_FIELD_FETCHED,pageDto);
    }

    @Override
    public ResponseDTO createCategory(CategoryRequestDTO dto) {
        Category category = categoryHelperService.saveCategory(dto);
        CategoryResponseDTO categoryResponseDTO = categoryMapper.toCustomerCategoryDTO(category);
        categoryResponseDTO.setMetadataFields( mapMetadataFields(category.getCategoryMetadataFieldValuesList()));
        return responseMapper.toResponseDto(HttpStatus.CREATED,MessageKeys.CATEGORY_ADDED, categoryResponseDTO);
    }

    @Override
    public ResponseDTO updateCategory(UpdateCategoryDTO dto) {
        categoryHelperService.updateCategory(dto);
        return responseMapper.toResponseDto(HttpStatus.OK,MessageKeys.CATEGORY_UPDATED,null);
    }

    @Override
    public ResponseDTO getCategory(String categoryId) {
        Category category = categoryHelperService.findById(categoryId);
        CategoryChainResponseDTO categoryChainResponseDTO = categoryMapper.toDTO(category);
        categoryChainResponseDTO.setParentCategory(mapParentCategories(category));
        categoryChainResponseDTO.setSubCategories(mapSubCategories(category.getSubCategories()));
        categoryChainResponseDTO.setMetadataFields( mapMetadataFields(category.getCategoryMetadataFieldValuesList()));
        return responseMapper.toResponseDto(HttpStatus.OK,MessageKeys.CATEGORY_FETCHED, categoryChainResponseDTO);
    }

    @Override
    public ResponseDTO getAllCategories(CategoryFilter filter, Pageable pageable) {
        Pageable effectivePageable = getEffectivePageable(pageable);
        Page<Category> categories = categoryRepo.findAll(
                CategorySpecification.build(filter),
                effectivePageable);
        log.debug("Fetched {} categories",categories.getNumberOfElements());
        Page<CategoryChainResponseDTO> listResponseDTOS = categories.map(MappingUtil::toFullDTO);
        return responseMapper.toResponseDto(HttpStatus.OK,MessageKeys.CATEGORY_FETCHED,listResponseDTOS);
    }

    @Override
    public ResponseDTO addMetadataFieldValue(AddCategoryMetadataRequestDTO dto) {
        Category category = categoryHelperService.findById(dto.getCategoryId());
        if(!category.isLeaf()){
            throw new IllegalArgumentException(MessageKeys.NOT_LEAF_CATEGORY);
        }
        List<FieldValueDTO> valueResponseDTOS = new ArrayList<>();
        for(FieldValueDTO fieldValueDTO : dto.getFields()) {
            CategoryMetadataField field = fieldRepo.findById(toUUID(fieldValueDTO.getFieldId()))
                    .orElseThrow(() -> {
                        log.error("Metadata Field not found with Id: {}",fieldValueDTO.getFieldId());
                        return new ResourceNotFoundException(MessageKeys.METADATA_FIELD_NOT_FOUND);
                    });

            CategoryMetadataFieldValuesId id = new CategoryMetadataFieldValuesId();
            id.setCategoryMetadataField(field.getId());
            id.setCategory(category.getId());
            Optional<CategoryMetadataFieldValues> values = valuesRepo.findById(id);
            if(values.isPresent()) {
                continue;
            }
            CategoryMetadataFieldValues newValue = new CategoryMetadataFieldValues();
            newValue.setCategory(category);
            newValue.setCategoryMetadataField(field);
            newValue.setValues(fieldValueDTO.getValues());
            valuesRepo.save(newValue);
            valueResponseDTOS.add(fieldValueDTO);
        }
        if(valueResponseDTOS.isEmpty()) {
            return responseMapper.toResponseDto(HttpStatus.OK,MessageKeys.NO_METADATA_FIELD_ADDED,null);
        }
        return responseMapper.toResponseDto(HttpStatus.OK,MessageKeys.METADATA_FIELD_ADDED_WITH_VALUES,valueResponseDTOS);
    }

    @Override
    public ResponseDTO changeMetadataValue(UpdateCategoryMetadataRequestDTO dto) {
        Category category = categoryHelperService.findById(dto.getCategoryId());
        if(!category.isLeaf()){
            log.error("Attempted to update metadata value on non leaf category with Id: {}", category.getId());
            throw new IllegalArgumentException(MessageKeys.NOT_LEAF_CATEGORY);
        }
        CategoryMetadataField field = fieldRepo.findById(toUUID(dto.getFields().getFieldId()))
                .orElseThrow(() -> {
                    log.error("Metadata Field not found with id: {}",dto.getFields().getFieldId());
                    return new ResourceNotFoundException(MessageKeys.METADATA_FIELD_NOT_FOUND);
                });
        CategoryMetadataFieldValuesId id = new CategoryMetadataFieldValuesId();
        id.setCategoryMetadataField(field.getId());
        id.setCategory(category.getId());
        CategoryMetadataFieldValues values = valuesRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Metadata Field Values not found with id: {}",id);
                    return new ResourceNotFoundException(MessageKeys.METADATA_FIELD_NOT_FOUND);
                });
        values.getValues().addAll(dto.getFields().getValues());
        valuesRepo.save(values);
        CategoryMetadataFieldValuesResponseDTO valuesDTO = categoryMapper.toDTO(values,field);
        return responseMapper.toResponseDto(HttpStatus.OK,MessageKeys.METADATA_FIELD_UPDATED_WITH_VALUES,valuesDTO);
    }
}
