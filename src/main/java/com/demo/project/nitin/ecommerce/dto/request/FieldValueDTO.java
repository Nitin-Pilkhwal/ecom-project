package com.demo.project.nitin.ecommerce.dto.request;

import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class FieldValueDTO {
    @NotNull(message = MessageKeys.FIELD_ID_REQUIRED)
    private String fieldId;

    @NotEmpty(message = MessageKeys.VALUES_REQUIRED)
    private Set<@NotBlank(message = MessageKeys.VALUES_NOT_BLANK) String> values;
}
