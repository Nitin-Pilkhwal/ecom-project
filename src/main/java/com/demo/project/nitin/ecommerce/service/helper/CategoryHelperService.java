package com.demo.project.nitin.ecommerce.service.helper;

import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import com.demo.project.nitin.ecommerce.dto.request.CategoryMetadataFieldRequestDTO;
import com.demo.project.nitin.ecommerce.dto.request.CategoryRequestDTO;
import com.demo.project.nitin.ecommerce.dto.request.update.UpdateCategoryDTO;
import com.demo.project.nitin.ecommerce.entity.Category;
import com.demo.project.nitin.ecommerce.entity.CategoryMetadataField;
import com.demo.project.nitin.ecommerce.entity.CategoryMetadataFieldValues;
import com.demo.project.nitin.ecommerce.exception.exceptions.DuplicateFieldException;
import com.demo.project.nitin.ecommerce.exception.exceptions.ResourceNotFoundException;
import com.demo.project.nitin.ecommerce.mapper.CategoryMapper;
import com.demo.project.nitin.ecommerce.repository.CategoryMetadataFieldRepo;
import com.demo.project.nitin.ecommerce.repository.CategoryMetadataFieldValuesRepo;
import com.demo.project.nitin.ecommerce.repository.CategoryRepo;
import com.demo.project.nitin.ecommerce.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static com.demo.project.nitin.ecommerce.utils.CommonUtil.checkDuplicate;
import static com.demo.project.nitin.ecommerce.utils.CommonUtil.toUUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryHelperService {

    private final CategoryMapper categoryMapper;
    private final CategoryMetadataFieldRepo categoryMetadataFieldRepo;
    private final CategoryRepo categoryRepo;
    private final ProductRepo productRepo;
    private final CategoryMetadataFieldValuesRepo categoryMetadataFieldValuesRepo;

    public CategoryMetadataField saveCategoryMetadataField(CategoryMetadataFieldRequestDTO dto) {
        checkDuplicate(categoryMetadataFieldRepo.findByName(dto.getName()), MessageKeys.METADATA_FIELD_NAME_EXISTS);
        CategoryMetadataField categoryMetadataField = categoryMapper.toEntity(dto);
        log.info("Saving categoryMetadataField with name: {}", categoryMetadataField.getName());
        return categoryMetadataFieldRepo.save(categoryMetadataField);
    }

    public Category saveCategory(CategoryRequestDTO dto) {
        Category category = categoryMapper.toEntity(dto);
        List<CategoryMetadataFieldValues> fieldValuesList = Collections.emptyList();
        if(dto.getParentCategoryId() != null) {
            Category parentCategory = findById(dto.getParentCategoryId());
            checkDuplicate(
                    productRepo.findByCategory(parentCategory),
                    MessageKeys.PRODUCT_EXISTS_FOR_PARENT_CATEGORY
            );
            checkDuplicate(
                    categoryRepo.findByParentCategoryAndName(parentCategory,category.getName()),
                    MessageKeys.CATEGORY_EXISTS_ON_SAME_LEVEL
            );
            if(categoryRepo.findByNameInCategoryHierarchy(parentCategory.getId(),dto.getName()) >=1){
                log.error("Category with name: {} exist in parent hierachy of Category: {}",dto.getName(),category.getId());
                throw new DuplicateFieldException(MessageKeys.CATEGORY_EXISTS_ON_SAME_HIERARCHY);
            }
            category.setParentCategory(parentCategory);
            parentCategory.setLeaf(false);
            categoryRepo.save(parentCategory);
            fieldValuesList = categoryMetadataFieldValuesRepo.findByCategory(parentCategory);
        }else{
            checkDuplicate(
                    categoryRepo.findByNameAndParentCategoryIsNull(dto.getName()),
                    MessageKeys.CATEGORY_EXISTS_ON_SAME_LEVEL
            );
        }
        log.info("Saving category with name: {}", category.getName());
        category = categoryRepo.save(category);
        Category finalCategory = category;
        List<CategoryMetadataFieldValues> copiedValues = fieldValuesList.stream()
                .map(fieldValues -> categoryMapper.toEntity(fieldValues, finalCategory))
                .toList();
        category.setCategoryMetadataFieldValuesList(categoryMetadataFieldValuesRepo.saveAll(copiedValues));
        return category;
    }

    public Category findById(String id){
        return categoryRepo.findById(toUUID(id))
                .orElseThrow(() ->{
                    log.error("Category not found with id: {}",id);
                    return new ResourceNotFoundException(MessageKeys.CATEGORY_NOT_FOUND);
                });
    }

    public void updateCategory(UpdateCategoryDTO dto) {
        Category category = findById(dto.getId());
        if(category.getParentCategory() != null) {
            Category parentCategory = category.getParentCategory();
            if(categoryRepo.findByNameInCategoryHierarchy(parentCategory.getId(),dto.getName()) >=1){
                log.error("Category with name: {} exist in parent hierachy of Category: {}",dto.getName(),category.getId());
                throw new DuplicateFieldException(MessageKeys.CATEGORY_EXISTS_ON_SAME_HIERARCHY);
            }
            checkDuplicate(
                    categoryRepo.findByParentCategoryAndName(parentCategory,dto.getName()),
                    MessageKeys.CATEGORY_EXISTS_ON_SAME_LEVEL
            );
        }
        else{
            checkDuplicate(
                    categoryRepo.findByNameAndParentCategoryIsNull(dto.getName()),
                    MessageKeys.CATEGORY_EXISTS_ON_SAME_LEVEL
            );
        }
        if((!category.isLeaf()) && categoryRepo.findSubCategoriesWithName(category.getId(),dto.getName()) >=1){
            log.error("Category with name: {}  exists in sub categories of category{}",dto.getName(),category.getId());
            throw new DuplicateFieldException(MessageKeys.CATEGORY_EXISTS_ON_SUB_CATEGORIES);
        }
        log.info("Updating category name from {} to {} for id: {}",category.getName(),dto.getName(),category.getId());
        category.setName(dto.getName());
        categoryRepo.save(category);
    }
}
