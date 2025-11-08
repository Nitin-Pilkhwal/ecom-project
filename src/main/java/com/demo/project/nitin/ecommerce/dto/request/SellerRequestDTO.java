package com.demo.project.nitin.ecommerce.dto.request;

import com.demo.project.nitin.ecommerce.constant.Constants;
import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import com.demo.project.nitin.ecommerce.utils.annotations.ValidatePhoneNumber;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.*;

@Setter
@Getter
public class SellerRequestDTO {

    @Pattern(regexp = Constants.Regex.EMAIL, message = MessageKeys.EMAIL_INVALID)
    @NotBlank(message = MessageKeys.EMAIL_REQUIRED)
    private String email;

    @NotBlank(message = MessageKeys.PASSWORD_REQUIRED)
    @Pattern(regexp = Constants.Regex.PASSWORD, message = MessageKeys.PASSWORD_PATTERN)
    private String password;

    @NotBlank(message = MessageKeys.PASSWORD_CONFIRM_REQUIRED)
    @Pattern(regexp = Constants.Regex.PASSWORD, message = MessageKeys.CONFIRM_PASSWORD_PATTERN)
    private String confirmPassword;

    @Pattern(regexp = Constants.Regex.GST, message = MessageKeys.GST_INVALID)
    private String gst;

    @NotBlank(message = MessageKeys.COMPANY_NAME_REQUIRED)
    @Pattern(regexp = Constants.Regex.COMPANY_NAME, message = MessageKeys.INVALID_COMPANY_NAME)
    private String companyName;

    @Valid
    @NotNull(message = MessageKeys.COMPANY_ADDRESS_REQUIRED)
    private AddressDTO companyAddress;

    @ValidatePhoneNumber(message = MessageKeys.COMPANY_CONTACT_INVALID)
    @NotBlank(message = MessageKeys.COMPANY_CONTACT_REQUIRED)
    private String companyContact;

    @Pattern(regexp = Constants.Regex.ALL_LANG_NAME, message = MessageKeys.INVALID_FIRST_NAME)
    @NotBlank(message = MessageKeys.FIRSTNAME_REQUIRED)
    private String firstName;

    @Pattern(regexp = Constants.Regex.ALL_LANG_NAME, message = MessageKeys.INVALID_LAST_NAME)
    @NotBlank(message = MessageKeys.LASTNAME_REQUIRED)
    private String lastName;

    @Pattern(regexp = Constants.Regex.ALL_LANG_NAME, message = MessageKeys.INVALID_MIDDLE_NAME)
    private String middleName;
}
