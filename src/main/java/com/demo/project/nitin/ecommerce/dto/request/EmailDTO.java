package com.demo.project.nitin.ecommerce.dto.request;

import com.demo.project.nitin.ecommerce.constant.Constants;
import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailDTO {

    @Pattern(regexp = Constants.Regex.EMAIL, message = MessageKeys.EMAIL_INVALID)
    @NotBlank(message = MessageKeys.EMAIL_REQUIRED)
    private String email;
}
