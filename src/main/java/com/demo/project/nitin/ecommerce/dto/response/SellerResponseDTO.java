package com.demo.project.nitin.ecommerce.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class SellerResponseDTO {

    private UUID id;

    private String email;

    private String gst;

    private String companyName;

    private AddressResponseDTO companyAddress;

    private String companyContact;

    private String firstName;

    private String lastName;

    private String middleName;

    private boolean isActive;
}
