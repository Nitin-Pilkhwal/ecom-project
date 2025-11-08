package com.demo.project.nitin.ecommerce.service.impl;

import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import com.demo.project.nitin.ecommerce.dto.request.update.PasswordChangeDTO;
import com.demo.project.nitin.ecommerce.dto.request.update.UpdateAddressDTO;
import com.demo.project.nitin.ecommerce.dto.response.ResponseDTO;
import com.demo.project.nitin.ecommerce.entity.Address;
import com.demo.project.nitin.ecommerce.entity.User;
import com.demo.project.nitin.ecommerce.exception.exceptions.ResourceNotFoundException;
import com.demo.project.nitin.ecommerce.mapper.ResponseMapper;
import com.demo.project.nitin.ecommerce.repository.AddressRepo;
import com.demo.project.nitin.ecommerce.service.UserService;
import com.demo.project.nitin.ecommerce.service.helper.NotificationHelperService;
import com.demo.project.nitin.ecommerce.service.helper.UserHelperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.demo.project.nitin.ecommerce.utils.CommonUtil.*;
import static com.demo.project.nitin.ecommerce.utils.MappingUtil.mapNonNullAddressFields;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserHelperService userHelperService;
    private final NotificationHelperService notificationHelperService;
    private final ResponseMapper responseMapper;
    private final AddressRepo addressRepo;

    @Override
    public ResponseDTO changePassword(String userName, PasswordChangeDTO dto) {
        User user = userHelperService.findByEmail(userName);
        userHelperService.validatePassword(dto, user);
        userHelperService.updatePassword(user, dto.getPassword());
        userHelperService.updateUser(user);
        notificationHelperService.sendPasswordUpdatedNotification(user);
        return responseMapper.toResponseDto(HttpStatus.OK, MessageKeys.PASSWORD_CHANGED, null);
    }

    @Override
    public ResponseDTO updateAddress(String userName, String addressId, UpdateAddressDTO updateAddressDTO) {
        User user = userHelperService.findByEmail(userName);
        Address address = addressRepo.findByIdAndUser(toUUID(addressId), user)
                .orElseThrow(() -> new ResourceNotFoundException(MessageKeys.ADDRESS_NOT_FOUND));
        log.info("Updated address for user: {}",user.getId());
        userHelperService.updateAddress(address,updateAddressDTO);
        Map<String, Object> updatedFields = mapNonNullAddressFields(updateAddressDTO);
        return responseMapper.toResponseDto(HttpStatus.OK,
                MessageKeys.ADDRESS_UPDATED,
                updatedFields);
    }
}
