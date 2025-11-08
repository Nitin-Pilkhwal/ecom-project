package com.demo.project.nitin.ecommerce.dto.request;

import com.demo.project.nitin.ecommerce.constant.Constants;
import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {

    @Pattern(regexp = Constants.Regex.EMAIL, message = MessageKeys.EMAIL_INVALID)
    @NotBlank(message = MessageKeys.USERNAME_REQUIRED)
    private String userName;

    @NotBlank(message = MessageKeys.PASSWORD_REQUIRED)
    @Pattern(regexp = Constants.Regex.PASSWORD, message = MessageKeys.PASSWORD_PATTERN)
    private String password;
}
