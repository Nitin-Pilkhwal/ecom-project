package com.demo.project.nitin.ecommerce.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AddressResponseDTO {

    private UUID id;

    private String city;

    private String state;

    private String country;

    private String addressLine;

    private String zipcode;

    private String label;

}
