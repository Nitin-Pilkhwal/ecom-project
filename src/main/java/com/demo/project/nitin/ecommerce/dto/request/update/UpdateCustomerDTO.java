package com.demo.project.nitin.ecommerce.dto.request.update;

import com.demo.project.nitin.ecommerce.constant.Constants;
import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import com.demo.project.nitin.ecommerce.utils.annotations.ValidatePhoneNumber;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateCustomerDTO {

    @ValidatePhoneNumber
    private String contact;

    @Pattern(regexp = Constants.Regex.ALL_LANG_NAME, message = MessageKeys.INVALID_FIRST_NAME)
    private String firstName;

    @Pattern(regexp = Constants.Regex.ALL_LANG_NAME, message = MessageKeys.INVALID_LAST_NAME)
    private String lastName;

    @Pattern(regexp = Constants.Regex.ALL_LANG_NAME, message = MessageKeys.INVALID_MIDDLE_NAME)
    private String middleName;
}

