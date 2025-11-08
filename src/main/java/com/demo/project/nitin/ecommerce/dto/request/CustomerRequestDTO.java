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
public class CustomerRequestDTO {

    @Pattern(regexp = Constants.Regex.EMAIL, message = MessageKeys.EMAIL_INVALID)
    @NotBlank(message = MessageKeys.EMAIL_REQUIRED)
    private String email;

    @ValidatePhoneNumber
    @NotBlank(message = MessageKeys.CONTACT_REQUIRED)
    private String contact;

    @NotBlank(message = MessageKeys.PASSWORD_REQUIRED)
    @Pattern(regexp = Constants.Regex.PASSWORD, message = MessageKeys.PASSWORD_PATTERN)
    private String password;

    @NotBlank(message = MessageKeys.PASSWORD_CONFIRM_REQUIRED)
    @Pattern(regexp = Constants.Regex.PASSWORD, message = MessageKeys.CONFIRM_PASSWORD_PATTERN)
    private String confirmPassword;

    @Pattern(regexp = Constants.Regex.ALL_LANG_NAME, message = MessageKeys.INVALID_FIRST_NAME)
    @NotBlank(message = MessageKeys.FIRSTNAME_REQUIRED)
    private String firstName;

    @Pattern(regexp = Constants.Regex.ALL_LANG_NAME, message = MessageKeys.INVALID_LAST_NAME)
    @NotBlank(message = MessageKeys.LASTNAME_REQUIRED)
    private String lastName;

    @Pattern(regexp = Constants.Regex.ALL_LANG_NAME, message = MessageKeys.INVALID_MIDDLE_NAME)
    private String middleName;

    @Valid
    @NotNull(message = MessageKeys.ADDRESS_REQUIRED)
    private AddressDTO address;
}

