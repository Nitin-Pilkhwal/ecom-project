package com.demo.project.nitin.ecommerce.dto.request;

import com.demo.project.nitin.ecommerce.constant.Constants;
import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequestDTO {

    @NotBlank(message = MessageKeys.CATEGORY_NAME_REQUIRED)
    @Pattern(regexp = Constants.Regex.GENERIC_NAME, message = MessageKeys.CATEGORY_NAME_INVALID)
    private String name;

    private String parentCategoryId;
}
