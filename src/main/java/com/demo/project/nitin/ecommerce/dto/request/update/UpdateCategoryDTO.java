package com.demo.project.nitin.ecommerce.dto.request.update;

import com.demo.project.nitin.ecommerce.constant.Constants;
import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCategoryDTO {

    @NotBlank(message = MessageKeys.ID_REQUIRED)
    @Pattern(regexp = Constants.Regex.ID,message = MessageKeys.INVALID_ID)
    private String id;

    @NotBlank(message = MessageKeys.CATEGORY_NAME_REQUIRED)
    @Pattern(regexp = Constants.Regex.GENERIC_NAME, message = MessageKeys.CATEGORY_NAME_INVALID)
    private String name;
}
