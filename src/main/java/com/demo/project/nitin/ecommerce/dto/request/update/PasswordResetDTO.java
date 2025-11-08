package com.demo.project.nitin.ecommerce.dto.request.update;

import com.demo.project.nitin.ecommerce.constant.Constants;
import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetDTO {

    @NotBlank(message = MessageKeys.PASSWORD_REQUIRED)
    @Pattern(regexp = Constants.Regex.PASSWORD, message = MessageKeys.PASSWORD_PATTERN)
    private String password;

    @NotBlank(message = MessageKeys.PASSWORD_CONFIRM_REQUIRED)
    @Pattern(regexp = Constants.Regex.PASSWORD, message = MessageKeys.CONFIRM_PASSWORD_PATTERN)
    private String confirmPassword;
}
