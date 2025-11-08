package com.demo.project.nitin.ecommerce.dto.filter;

import com.demo.project.nitin.ecommerce.constant.Constants;
import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerFilter {

    @Pattern(regexp = Constants.Regex.EMAIL, message = MessageKeys.EMAIL_INVALID)
    private String email;

    @Pattern(regexp = Constants.Regex.ALL_LANG_NAME, message = MessageKeys.INVALID_FIRST_NAME)
    private String firstName;

    @Pattern(regexp = Constants.Regex.ALL_LANG_NAME, message = MessageKeys.INVALID_LAST_NAME)
    private String lastName;

    @Pattern(regexp = Constants.Regex.ALL_LANG_NAME, message = MessageKeys.INVALID_MIDDLE_NAME)
    private String middleName;

    @Pattern(regexp = Constants.Regex.COUNTRY_CODE, message = MessageKeys.COUNTRY_CODE_INVALID)
    private String countryCode;

    private Boolean isActive;
}
