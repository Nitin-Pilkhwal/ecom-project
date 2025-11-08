package com.demo.project.nitin.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String accessToken;

    @NotBlank(message = "Refresh token cannot be empty")
    private String refreshToken;
}
