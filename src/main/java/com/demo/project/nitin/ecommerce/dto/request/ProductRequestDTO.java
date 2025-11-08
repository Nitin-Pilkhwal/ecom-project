package com.demo.project.nitin.ecommerce.dto.request;

import com.demo.project.nitin.ecommerce.constant.Constants;
import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDTO {

    @NotBlank(message = MessageKeys.NAME_REQUIRED)
    @Pattern(regexp = Constants.Regex.COMPANY_NAME, message = MessageKeys.NAME_INVALID)
    private String name;

    @NotBlank(message = MessageKeys.DESCRIPTION_REQUIRED)
    @Pattern(regexp = Constants.Regex.COMPANY_NAME, message = MessageKeys.DESCRIPTION_INVALID)
    private String description;

    @NotBlank(message = MessageKeys.BRAND_REQUIRED)
    @Pattern(regexp = Constants.Regex.COMPANY_NAME, message = MessageKeys.BRAND_INVALID)
    private String brand;

    @NotBlank(message = MessageKeys.CATEGORY_ID_REQUIRED)
    @Pattern(regexp = Constants.Regex.ID,message = MessageKeys.INVALID_ID)
    private String categoryId;

    private Boolean isCancellable;

    private Boolean isReturnable;
}
