package com.demo.project.nitin.ecommerce.service.impl;

import com.demo.project.nitin.ecommerce.auth.CustomUserDetails;
import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import com.demo.project.nitin.ecommerce.constant.enums.TokenType;
import com.demo.project.nitin.ecommerce.dto.request.CustomerRequestDTO;
import com.demo.project.nitin.ecommerce.dto.request.LoginRequestDTO;
import com.demo.project.nitin.ecommerce.dto.request.update.PasswordResetDTO;
import com.demo.project.nitin.ecommerce.dto.request.SellerRequestDTO;
import com.demo.project.nitin.ecommerce.dto.response.CustomerResponseDTO;
import com.demo.project.nitin.ecommerce.dto.response.ResponseDTO;
import com.demo.project.nitin.ecommerce.dto.response.SellerResponseDTO;
import com.demo.project.nitin.ecommerce.exception.exceptions.ResourceNotFoundException;
import com.demo.project.nitin.ecommerce.mapper.ResponseMapper;
import com.demo.project.nitin.ecommerce.mapper.UserMapper;
import com.demo.project.nitin.ecommerce.repository.CustomerRepo;
import com.demo.project.nitin.ecommerce.utils.JwtUtils;
import com.demo.project.nitin.ecommerce.cache.TokenCache;
import com.demo.project.nitin.ecommerce.constant.Constants;
import com.demo.project.nitin.ecommerce.dto.*;
import com.demo.project.nitin.ecommerce.entity.Customer;
import com.demo.project.nitin.ecommerce.entity.Token;
import com.demo.project.nitin.ecommerce.entity.Seller;
import com.demo.project.nitin.ecommerce.entity.User;
import com.demo.project.nitin.ecommerce.exception.exceptions.TokenExpiredException;
import com.demo.project.nitin.ecommerce.service.AuthService;
import com.demo.project.nitin.ecommerce.service.helper.NotificationHelperService;
import com.demo.project.nitin.ecommerce.service.helper.TokenHelperService;
import com.demo.project.nitin.ecommerce.service.helper.UserHelperService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.demo.project.nitin.ecommerce.utils.CommonUtil.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserHelperService userHelperService;
    private final JwtUtils jwtUtils;
    private final NotificationHelperService notificationHelperService;
    private final AuthenticationManager authenticationManager;
    private final TokenHelperService tokenHelperService;
    private final TokenCache tokenCache;
    private final ResponseMapper responseMapper;
    private final UserMapper userMapper;
    private final CustomerRepo customerRepo;

    @Override
    public ResponseDTO registerCustomer(CustomerRequestDTO dto) {
        Customer customer = userHelperService.saveCustomer(dto);
        notificationHelperService.sendActivationNotification(customer);
        CustomerResponseDTO customerResponseDTO = userMapper.toDto(customer);
        log.info("Customer with email {} is successfully registered.",customer.getEmail());
        return responseMapper.toResponseDto(HttpStatus.CREATED, MessageKeys.USER_REGISTERED, customerResponseDTO);
    }

    @Override
    public ResponseDTO registerSeller(SellerRequestDTO dto) {
        Seller seller = userHelperService.saveSeller(dto);
        notificationHelperService.sendAccountCreatedNotification(seller);
        SellerResponseDTO sellerResponseDTO = userMapper.toDto(seller);
        log.info("Seller with email {} is successfully registered.",seller.getEmail());
        return responseMapper.toResponseDto(HttpStatus.CREATED, MessageKeys.USER_REGISTERED, sellerResponseDTO);
    }

    @Override
    public ResponseDTO activateCustomer(String token) {
        Token tokenEntity = tokenHelperService.findByToken(token, TokenType.ACTIVATION);
        User user = tokenEntity.getUser();
        try {
            tokenHelperService.verifyExpiration(tokenEntity);
            userHelperService.activateUser(user);
            tokenHelperService.deleteById(tokenEntity.getId().toString());
            notificationHelperService.sendAccountActivatedNotification(user);
            log.info("Customer with email {} has been successfully activated with activation link",user.getEmail());
        }catch (TokenExpiredException e){
            log.error("Activation token expired for user: {}. Resending activation link.", user.getEmail());
            resendActivationLink(user.getEmail());
            throw new TokenExpiredException(MessageKeys.ACTIVATION_TOKEN_EXPIRED_RESEND_EMAIL);
        }
        return responseMapper.toResponseDto(HttpStatus.OK, MessageKeys.USER_ACTIVATED, null);
    }

    @Override
    public ResponseDTO resendActivationLink(String email) {
        User user = customerRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(MessageKeys.CUSTOMER_NOT_FOUND));
        if(user.isActive()){
            log.error("User with email {} is already active.", email);
            throw new IllegalStateException(MessageKeys.USER_ALREADY_ACTIVE);
        }
        notificationHelperService.sendActivationNotification(user);
        log.info("Activation link ");
        return responseMapper.toResponseDto(HttpStatus.OK, MessageKeys.ACTIVATION_LINK_RESENT, null);
    }

    @Override
    public ResponseDTO userLogin(LoginRequestDTO loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUserName(), loginRequestDto.getPassword())
        );
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        TokenDTO tokenDTO = tokenHelperService.createTokenPair(customUserDetails.getUser());
        return responseMapper.toResponseDto(HttpStatus.OK, MessageKeys.USER_LOGGED_IN, tokenDTO);
    }

    @Override
    public ResponseDTO userLogout(String token) {
        Claims claims = jwtUtils.extractallclaims(
                token.substring(Constants.Security.AUTHORIZATION_HEADER_PREFIX.length())
        );
        String username = claims.getSubject();
        if(tokenCache.isTokenBlackListed(claims.getId())) {
            log.error("Token is already blacklisted.");
            throw new ExpiredJwtException(null, claims, MessageKeys.TOKEN_ALREADY_EXPIRED);
        }
        tokenCache.blackListToken(claims.getId());
        tokenHelperService.deleteById(claims.getId());
        log.info("User {} logged out successfully and refresh token deleted.", username);
        return responseMapper.toResponseDto(HttpStatus.OK, MessageKeys.USER_LOGGED_OUT, null);
    }

    @Override
    public ResponseDTO generateNewAccessToken(String requestRefreshToken) {
        Claims claims = jwtUtils.extractallclaims(requestRefreshToken);
        String tokenValue = claims.getId();
        Token tokenEntity = tokenHelperService.findByToken(tokenValue,TokenType.REFRESH);
        tokenHelperService.deleteById(tokenEntity.getId().toString());
        validateUser(tokenEntity.getUser());
        TokenDTO tokenDTO = tokenHelperService.createTokenPair(tokenEntity.getUser());
        log.info("New access token generated for {} ",tokenEntity.getUser());
        return responseMapper.toResponseDto(HttpStatus.OK, MessageKeys.ACCESS_TOKEN_GENERATED, tokenDTO);
    }

    private void validateUser(User user) {
        if(user.isLocked()) {
            log.error("Account: {} is locked", user.getEmail());
            throw new LockedException(MessageKeys.USER_ACCOUNT_LOCKED);
        }
        if(!user.isActive()){
            log.error("Account: {} is not active", user.getEmail());
            throw new DisabledException(MessageKeys.USER_ACCOUNT_NOT_ACTIVE);
        }
        if (user.getPasswordUpdateDate() != null &&
                user.getPasswordUpdateDate().isBefore(LocalDate.now().minusDays(Constants.Security.PASSWORD_EXPIRY_DAYS))) {
            log.error("Credentials for user: {} are expired", user.getEmail());
            throw new CredentialsExpiredException(MessageKeys.USER_CREDENTIALS_EXPIRED);
        }
        if(user.isExpired()){
            log.error("Account: {} is expired", user.getEmail());
            throw new AccountExpiredException(MessageKeys.USER_ACCOUNT_EXPIRED);
        }
    }

    @Override
    public ResponseDTO initiateForgotPassword(String email) {
        log.info("Initiate forgot password for user: {}", email);
        User user = userHelperService.findByEmail(email);
        if(!user.isActive()) {
            log.error("User with email {} is not active.", email);
            throw new IllegalStateException(MessageKeys.USER_ACCOUNT_NOT_ACTIVE);
        }
        notificationHelperService.sendPasswordResetNotification(user);
        return responseMapper.toResponseDto(HttpStatus.OK, MessageKeys.PASSWORD_RESET_LINK_SENT, null);
    }

    @Override
    public ResponseDTO resetPassword(String token, PasswordResetDTO passwordResetDTO) {
        matchPassword(passwordResetDTO.getPassword(), passwordResetDTO.getConfirmPassword());
        Token tokenEntity = tokenHelperService.findByToken(token,TokenType.FORGOT_PASSWORD);
        User user = tokenEntity.getUser();
        try {
            tokenHelperService.verifyExpiration(tokenEntity);
            tokenHelperService.deleteAllTokensByUser(user);
            userHelperService.updatePassword(user, passwordResetDTO.getPassword());
            user.setLocked(false);
            user.setExpired(false);
            user.setInvalidAttemptCount(0);
            userHelperService.updateUser(user);
            notificationHelperService.sendPasswordUpdatedNotification(user);
            log.info("Password reset successfully for user: {}", user.getEmail());
        } catch (TokenExpiredException e) {
            log.error("Password reset token expired for user: {}.", user.getEmail());
            throw e;
        }
        return responseMapper.toResponseDto(HttpStatus.OK, MessageKeys.PASSWORD_RESET, null);
    }
}
