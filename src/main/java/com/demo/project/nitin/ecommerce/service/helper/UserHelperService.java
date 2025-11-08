package com.demo.project.nitin.ecommerce.service.helper;

import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import com.demo.project.nitin.ecommerce.constant.enums.Authority;
import com.demo.project.nitin.ecommerce.dto.request.*;
import com.demo.project.nitin.ecommerce.dto.request.update.PasswordChangeDTO;
import com.demo.project.nitin.ecommerce.dto.request.update.UpdateAddressDTO;
import com.demo.project.nitin.ecommerce.dto.request.update.UpdateSellerDTO;
import com.demo.project.nitin.ecommerce.entity.*;
import com.demo.project.nitin.ecommerce.exception.exceptions.DuplicateFieldException;
import com.demo.project.nitin.ecommerce.exception.exceptions.ResourceNotFoundException;
import com.demo.project.nitin.ecommerce.mapper.AddressMapper;
import com.demo.project.nitin.ecommerce.mapper.UserMapper;
import com.demo.project.nitin.ecommerce.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.demo.project.nitin.ecommerce.utils.CommonUtil.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserHelperService {

    private final UserRepo userRepo;
    private final CustomerRepo customerRepo;
    private final SellerRepo sellerRepo;
    private final RoleRepo roleRepo;
    private final AddressRepo addressRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AddressMapper addressMapper;
    private final FileHelperService fileHelperService;

    public Customer saveCustomer(CustomerRequestDTO dto) {
        validateUser(dto);
        Customer customer = userMapper.toEntity(dto,passwordEncoder);
        setRoles(customer,Authority.CUSTOMER);
        setAddress(customer,dto.getAddress());
        log.info("Saving customer '{}'", customer.getEmail());
        return customerRepo.save(customer);
    }

    public Seller saveSeller(SellerRequestDTO dto) {
        validateUser(dto);
        Seller seller = userMapper.toEntity(dto, passwordEncoder);
        setRoles(seller,Authority.SELLER);
        setAddress(seller, dto.getCompanyAddress());
        log.info("Saving seller '{}'", seller.getEmail());
        return sellerRepo.save(seller);
    }

    public void updateUser(User user){
        userRepo.save(user);
    }

    public void updatePassword(User user, String password) {
        log.info("Password updating for user: {}",user.getId());
        user.setPassword(passwordEncoder.encode(password));
        user.setPasswordUpdateDate(LocalDate.now());
    }

    public void updateAddress(Address address, UpdateAddressDTO dto) {
        addressMapper.updateAddressFromDto(dto,address);
        addressRepo.save(address);
    }

    public void activateUser(User user) {
        if(user.isActive()){
            log.error("User with email {} is already active.", user.getEmail());
            throw new IllegalStateException(MessageKeys.USER_ALREADY_ACTIVE);
        }
        log.info("Activating user '{}'", user.getEmail());
        user.setActive(true);
        user.setPasswordUpdateDate(LocalDate.now());
        updateUser(user);
    }

    public void deactivateUser(User user) {
        if(!user.isActive()){
            log.error("User with email {} is already deactivate.", user.getEmail());
            throw new IllegalStateException(MessageKeys.USER_ALREADY_DEACTIVATED);
        }
        log.info("Deactivating user '{}'", user.getEmail());
        user.setActive(false);
        user.setPasswordUpdateDate(null);
        updateUser(user);
    }

    public void setAddress(User user, AddressDTO addressDTO) {
        if (addressDTO != null) {
            Address address = addressMapper.toEntity(addressDTO);
            addressRepo.save(address);
            user.setAddresses(List.of(address));
        }
    }

    public User findByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("User with email {} not found.", email);
                    return new ResourceNotFoundException(MessageKeys.USER_NOT_FOUND);
                });
    }

    private void setRoles(User customer,Authority authority) {
        List<Role> rolesList = new ArrayList<>();
        roleRepo.findByAuthority(authority).ifPresent(rolesList::add);
        customer.setRoles(rolesList);
    }

    private void validateUser(Object dto) {
        if(dto instanceof CustomerRequestDTO customerRequestDTO){
            existsByEmail(customerRequestDTO.getEmail());
            matchPassword(customerRequestDTO.getPassword(), customerRequestDTO.getConfirmPassword());
        }else if(dto instanceof SellerRequestDTO sellerRequestDTO){
            existsByEmail(sellerRequestDTO.getEmail());
            matchPassword(sellerRequestDTO.getPassword(), sellerRequestDTO.getConfirmPassword());
            checkDuplicate(sellerRepo.findByCompanyNameOrGst(sellerRequestDTO.getCompanyName(),sellerRequestDTO.getGst()),
                    MessageKeys.COMPANY_UNIQUE);
        }
    }

    public void validateSellerUpdate(User user, UpdateSellerDTO dto) {
        checkUnique(dto.getCompanyName(), user.getId(),
                sellerRepo::existsByCompanyNameAndIdNot,
                MessageKeys.COMPANY_NAME_EXISTS);
        checkUnique(dto.getGst(), user.getId(),
                sellerRepo::existsByGstAndIdNot,
                MessageKeys.GST_EXISTS);
    }

    public void validatePassword(PasswordChangeDTO dto, User user) {
        matchOldPassword(user, dto.getOldPassword());
        matchPassword(dto.getPassword(), dto.getConfirmPassword());
        if(dto.getPassword().equals(dto.getOldPassword())){
            log.error("New password is same as old password");
            throw new IllegalArgumentException(MessageKeys.SAME_PASSWORD);
        }
    }

    public void matchOldPassword(User user, String oldPassword) {
        String currentPassword = user.getPassword();
        if(!passwordEncoder.matches(oldPassword, currentPassword)){
            log.error("Current password doesn't match");
            throw new IllegalArgumentException(MessageKeys.OLD_PASSWORD_MISMATCH);
        }
    }

    public String profileImage(String id){
        String path = fileHelperService.userProfileImagePath(id);
        if(path == null){
            return null;
        }
        return path.substring(path.lastIndexOf('/') + 1);
    }

    public User findById(String userId) {
        return userRepo.findById(toUUID(userId))
                .orElseThrow(() -> {
                    log.error("User with id {} not found.", userId);
                    return new ResourceNotFoundException(MessageKeys.USER_NOT_FOUND);
                });
    }

    public void existsByEmail(String email) {
        if(userRepo.existsByEmail(email)){
            log.error("User with email {} already exists.", email);
            throw new DuplicateFieldException(MessageKeys.EMAIL_EXISTS);
        }
    }

    public Seller findSellerByEmail(String email) {
        return sellerRepo.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("Seller with email {} not found.", email);
                    return new ResourceNotFoundException(MessageKeys.SELLER_NOT_FOUND);
                });
    }

    public Seller findSellerById(String sellerId) {
        return sellerRepo.findById(toUUID(sellerId))
                .orElseThrow(() -> {
                    log.error("Seller with ID {} not found.", sellerId);
                    return new ResourceNotFoundException(MessageKeys.SELLER_NOT_FOUND);
                });
    }
}
