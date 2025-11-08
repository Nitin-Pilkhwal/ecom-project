package com.demo.project.nitin.ecommerce.utils;

import com.demo.project.nitin.ecommerce.constant.Constants;
import com.demo.project.nitin.ecommerce.dto.request.update.UpdateAddressDTO;
import com.demo.project.nitin.ecommerce.dto.request.update.UpdateCustomerDTO;
import com.demo.project.nitin.ecommerce.dto.request.update.UpdateSellerDTO;
import com.demo.project.nitin.ecommerce.dto.response.CategoryChainResponseDTO;
import com.demo.project.nitin.ecommerce.dto.response.CategoryMetadataFieldValuesResponseDTO;
import com.demo.project.nitin.ecommerce.dto.response.CategoryResponseDTO;
import com.demo.project.nitin.ecommerce.dto.response.ParentCategoryDTO;
import com.demo.project.nitin.ecommerce.entity.Category;
import com.demo.project.nitin.ecommerce.entity.CategoryMetadataFieldValues;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MappingUtil {

    private MappingUtil() {}

    public static Map<String, Object> mapNonNullAddressFields(UpdateAddressDTO dto) {
        Map<String, Object> updatedFields = new HashMap<>();
        putIfNotNull(updatedFields, Constants.AddressFields.CITY, dto.getCity());
        putIfNotNull(updatedFields, Constants.AddressFields.STATE, dto.getState());
        putIfNotNull(updatedFields, Constants.AddressFields.COUNTRY, dto.getCountry());
        putIfNotNull(updatedFields, Constants.AddressFields.ADDRESS_LINE, dto.getAddressLine());
        putIfNotNull(updatedFields, Constants.AddressFields.ZIPCODE, dto.getZipcode());
        putIfNotNull(updatedFields, Constants.AddressFields.LABEL, dto.getLabel());
        return updatedFields;
    }

    public static Map<String, Object> mapNonNullUserFields(UpdateSellerDTO dto) {
        Map<String, Object> updatedFields = new HashMap<>();
        putIfNotNull(updatedFields, Constants.UserFields.FIRST_NAME, dto.getFirstName());
        putIfNotNull(updatedFields, Constants.UserFields.LAST_NAME, dto.getLastName());
        putIfNotNull(updatedFields, Constants.UserFields.MIDDLE_NAME, dto.getMiddleName());
        putIfNotNull(updatedFields, Constants.UserFields.COMPANY_NAME, dto.getCompanyName());
        putIfNotNull(updatedFields, Constants.UserFields.COMPANY_CONTACT, dto.getCompanyContact());
        putIfNotNull(updatedFields, Constants.UserFields.GST, dto.getGst());
        return updatedFields;
    }

    public static Map<String, Object> mapNonNullUserFields(UpdateCustomerDTO dto) {
        Map<String, Object> updatedFields = new HashMap<>();
        putIfNotNull(updatedFields, Constants.UserFields.FIRST_NAME, dto.getFirstName());
        putIfNotNull(updatedFields, Constants.UserFields.LAST_NAME, dto.getLastName());
        putIfNotNull(updatedFields, Constants.UserFields.MIDDLE_NAME, dto.getMiddleName());
        putIfNotNull(updatedFields, Constants.UserFields.CONTACT, dto.getContact());
        return updatedFields;
    }

    private static void putIfNotNull(Map<String, Object> map, String key, Object value) {
        if (value != null) {
            map.put(key, value);
        }
    }


    public static List<CategoryResponseDTO> mapSubCategories(List<Category> subCategories) {
        if (subCategories == null) return Collections.emptyList();
        return subCategories.stream()
                .map(sc -> {
                    CategoryResponseDTO dto = new CategoryResponseDTO();
                    dto.setId(sc.getId());
                    dto.setName(sc.getName());
                    dto.setMetadataFields(mapMetadataFields(sc.getCategoryMetadataFieldValuesList()));
                    return dto;
                }).toList();
    }

    public static List<CategoryMetadataFieldValuesResponseDTO> mapMetadataFields(List<CategoryMetadataFieldValues> fieldValues) {
        if (fieldValues == null) return Collections.emptyList();
        return fieldValues.stream().map(fv -> {
            CategoryMetadataFieldValuesResponseDTO dto = new CategoryMetadataFieldValuesResponseDTO();
            dto.setId(fv.getCategoryMetadataField().getId().toString());
            dto.setFieldName(fv.getCategoryMetadataField().getName());
            dto.setValues(fv.getValues());
            return dto;
        }).toList();
    }

    public static ParentCategoryDTO mapParentCategories(Category category) {
        if (category == null || category.getParentCategory() == null) {
            return null;
        }
        Category parent = category.getParentCategory();
        ParentCategoryDTO dto = new ParentCategoryDTO();
        dto.setId(parent.getId());
        dto.setName(parent.getName());
        dto.setParentCategory(mapParentCategories(parent));
        return dto;
    }
    public static CategoryChainResponseDTO toFullDTO(Category category) {
        if (category == null) return null;
        CategoryChainResponseDTO dto = new CategoryChainResponseDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setLeaf(category.isLeaf());
        dto.setParentCategory(mapParentCategories(category));
        dto.setSubCategories(mapSubCategories(category.getSubCategories()));
        dto.setMetadataFields(mapMetadataFields(category.getCategoryMetadataFieldValuesList()));
        return dto;
    }
}
