package com.demo.project.nitin.ecommerce.dto.request;

import com.demo.project.nitin.ecommerce.constant.Constants;
import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {

    @NotBlank(message = MessageKeys.CITY_REQUIRED)
    @Pattern(regexp = Constants.Regex.ADDRESS_NAME, message = MessageKeys.CITY_INVALID)
    private String city;

    @NotBlank(message = MessageKeys.STATE_REQUIRED)
    @Pattern(regexp = Constants.Regex.ADDRESS_NAME, message = MessageKeys.STATE_INVALID)
    private String state;

    @NotBlank(message = MessageKeys.COUNTRY_REQUIRED)
    @Pattern(regexp = Constants.Regex.ADDRESS_NAME, message = MessageKeys.COUNTRY_INVALID)
    private String country;

    @NotBlank(message = MessageKeys.ADDRESS_LINE_REQUIRED)
    private String addressLine;

    @NotBlank(message = MessageKeys.ZIPCODE_REQUIRED)
    @Pattern(regexp = Constants.Regex.ZIPCODE, message = MessageKeys.ZIPCODE_INVALID)
    private String zipcode;

    @NotBlank(message = MessageKeys.LABEL_REQUIRED)
    @Pattern(regexp = Constants.Regex.ADDRESS_NAME, message = MessageKeys.LABEL_INVALID)
    private String label;

}
