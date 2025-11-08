package com.demo.project.nitin.ecommerce.service;

import com.demo.project.nitin.ecommerce.dto.request.update.UpdateSellerDTO;
import com.demo.project.nitin.ecommerce.dto.response.ResponseDTO;

public interface SellerService {
    ResponseDTO viewProfile(String userName);

    ResponseDTO updateProfile(String userName, UpdateSellerDTO dto);
}
