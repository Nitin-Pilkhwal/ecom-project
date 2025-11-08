package com.demo.project.nitin.ecommerce.service;

import com.demo.project.nitin.ecommerce.dto.request.CustomerRequestDTO;
import com.demo.project.nitin.ecommerce.dto.request.LoginRequestDTO;
import com.demo.project.nitin.ecommerce.dto.request.update.PasswordResetDTO;
import com.demo.project.nitin.ecommerce.dto.request.SellerRequestDTO;
import com.demo.project.nitin.ecommerce.dto.response.ResponseDTO;

public interface AuthService {
    ResponseDTO registerCustomer(CustomerRequestDTO dto);

    ResponseDTO registerSeller(SellerRequestDTO dto);

    ResponseDTO activateCustomer(String token);

    ResponseDTO resendActivationLink(String email);

    ResponseDTO userLogin(LoginRequestDTO loginRequestDto);

    ResponseDTO userLogout(String token);

    ResponseDTO generateNewAccessToken(String refreshToken);

    ResponseDTO initiateForgotPassword(String email);

    ResponseDTO resetPassword(String token, PasswordResetDTO passwordResetDTO);
}
