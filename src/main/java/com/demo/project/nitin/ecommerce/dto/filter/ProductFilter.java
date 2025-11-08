package com.demo.project.nitin.ecommerce.dto.filter;

import com.demo.project.nitin.ecommerce.constant.Constants;
import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductFilter {

    @Pattern(regexp = Constants.Regex.COMPANY_NAME, message = MessageKeys.NAME_INVALID)
    private String name;

    @Pattern(regexp = Constants.Regex.COMPANY_NAME, message = MessageKeys.BRAND_INVALID)
    private String brand;

    @Pattern(regexp = Constants.Regex.ID,message = MessageKeys.INVALID_ID)
    private String categoryId;

    private Boolean isCancellable;

    private Boolean isReturnable;

    private Boolean isActive;
}
