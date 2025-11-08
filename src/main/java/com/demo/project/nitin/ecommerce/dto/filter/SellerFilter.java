package com.demo.project.nitin.ecommerce.dto.filter;

import com.demo.project.nitin.ecommerce.constant.Constants;
import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SellerFilter {
    private String email;

    @Pattern(regexp = Constants.Regex.GST, message = MessageKeys.GST_INVALID)
    private String gst;

    @Pattern(regexp = Constants.Regex.COMPANY_NAME, message = MessageKeys.INVALID_COMPANY_NAME)
    private String companyName;

    @Pattern(regexp = Constants.Regex.COUNTRY_CODE, message = MessageKeys.COUNTRY_CODE_INVALID)
    private String countryCode;

    @Pattern(regexp = Constants.Regex.ALL_LANG_NAME, message = MessageKeys.INVALID_FIRST_NAME)
    private String firstName;

    @Pattern(regexp = Constants.Regex.ALL_LANG_NAME, message = MessageKeys.INVALID_LAST_NAME)
    private String lastName;

    @Pattern(regexp = Constants.Regex.ALL_LANG_NAME, message = MessageKeys.INVALID_MIDDLE_NAME)
    private String middleName;

    private Boolean isActive;
}
