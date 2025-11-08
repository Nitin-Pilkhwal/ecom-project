package com.demo.project.nitin.ecommerce.dto.request.update;

import com.demo.project.nitin.ecommerce.constant.Constants;
import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProductDTO {

    @Pattern(regexp = Constants.Regex.COMPANY_NAME, message = MessageKeys.NAME_INVALID)
    private String name;

    @Pattern(regexp = Constants.Regex.COMPANY_NAME, message = MessageKeys.DESCRIPTION_INVALID)
    private String description;

    private Boolean isCancellable;

    private Boolean isReturnable;
}
