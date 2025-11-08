package com.demo.project.nitin.ecommerce.dto.request.update;

import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import com.demo.project.nitin.ecommerce.dto.request.FieldValueDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCategoryMetadataRequestDTO {

    @NotNull(message = MessageKeys.CATEGORY_ID_REQUIRED)
    private String categoryId;

    @Valid
    @NotNull(message = MessageKeys.FIELDS_REQUIRED)
    private FieldValueDTO fields;
}
