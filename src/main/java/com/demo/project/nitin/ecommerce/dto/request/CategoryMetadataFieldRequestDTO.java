package com.demo.project.nitin.ecommerce.dto.request;

import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryMetadataFieldRequestDTO {

    @NotBlank(message = MessageKeys.NAME_REQUIRED)
    @Pattern(regexp = "^[A-Za-z]+( [A-Za-z]+)?$",message = MessageKeys.INVALID_NAME)
    private String name;
}
