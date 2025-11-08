package com.demo.project.nitin.ecommerce.dto.filter;

import com.demo.project.nitin.ecommerce.constant.Constants;
import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryFilter {

    @Pattern(regexp = Constants.Regex.GENERIC_NAME, message = MessageKeys.CATEGORY_NAME_INVALID)
    private String name;

    @Pattern(regexp = Constants.Regex.ID,message = MessageKeys.INVALID_ID)
    private String parentId;

    private Boolean leaf;
}