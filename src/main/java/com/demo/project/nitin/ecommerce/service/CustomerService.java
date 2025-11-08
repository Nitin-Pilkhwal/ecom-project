package com.demo.project.nitin.ecommerce.service;

import com.demo.project.nitin.ecommerce.dto.request.AddressDTO;
import com.demo.project.nitin.ecommerce.dto.request.update.UpdateCustomerDTO;
import com.demo.project.nitin.ecommerce.dto.response.ResponseDTO;

public interface CustomerService {

    ResponseDTO addAddress(String userName,AddressDTO addressDTO);

    ResponseDTO removeAddress(String userName, String addressId);

    ResponseDTO getAddresses(String userName);

    ResponseDTO viewProfile(String userName);

    ResponseDTO updateProfile(String name, UpdateCustomerDTO dto);
}
