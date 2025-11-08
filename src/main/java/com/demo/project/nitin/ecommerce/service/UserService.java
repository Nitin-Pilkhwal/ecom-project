package com.demo.project.nitin.ecommerce.service;

import com.demo.project.nitin.ecommerce.dto.request.update.PasswordChangeDTO;
import com.demo.project.nitin.ecommerce.dto.response.ResponseDTO;
import com.demo.project.nitin.ecommerce.dto.request.update.UpdateAddressDTO;

public interface UserService {

    ResponseDTO changePassword(String name, PasswordChangeDTO passwordResetDTO);

    ResponseDTO updateAddress(String userName,String addressId, UpdateAddressDTO updateAddressDTO);
}
