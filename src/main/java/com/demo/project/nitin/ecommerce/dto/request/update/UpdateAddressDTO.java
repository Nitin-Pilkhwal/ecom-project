package com.demo.project.nitin.ecommerce.dto.request.update;
import com.demo.project.nitin.ecommerce.constant.Constants;
import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAddressDTO {
    
    @Pattern(regexp = Constants.Regex.ADDRESS_NAME, message = MessageKeys.CITY_INVALID)
    private String city;

    @Pattern(regexp = Constants.Regex.ADDRESS_NAME, message = MessageKeys.STATE_INVALID)
    private String state;

    @Pattern(regexp = Constants.Regex.ADDRESS_NAME, message = MessageKeys.COUNTRY_INVALID)
    private String country;

    private String addressLine;

    @Pattern(regexp = Constants.Regex.ZIPCODE, message = MessageKeys.ZIPCODE_INVALID)
    private String zipcode;

    @Pattern(regexp = Constants.Regex.ADDRESS_NAME, message = MessageKeys.LABEL_INVALID)
    private String label;
    
}
