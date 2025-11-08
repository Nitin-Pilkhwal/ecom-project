package com.demo.project.nitin.ecommerce.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CustomerProfileDTO {

    private UUID id;

    private String email;

    private String contact;

    private String firstName;

    private String lastName;

    private String middleName;

    private String image;

    private boolean isActive;
}
