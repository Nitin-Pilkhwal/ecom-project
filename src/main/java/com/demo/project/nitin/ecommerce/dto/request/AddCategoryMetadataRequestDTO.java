package com.demo.project.nitin.ecommerce.dto.request;

import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddCategoryMetadataRequestDTO {

    @NotNull(message = MessageKeys.CATEGORY_ID_REQUIRED)
    private String categoryId;

    @NotEmpty(message = MessageKeys.FIELDS_REQUIRED)
    private List<FieldValueDTO> fields;
}
