package com.demo.project.nitin.ecommerce.service.impl.customer;

import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import com.demo.project.nitin.ecommerce.dto.request.AddressDTO;
import com.demo.project.nitin.ecommerce.dto.request.update.UpdateCustomerDTO;
import com.demo.project.nitin.ecommerce.dto.response.AddressResponseDTO;
import com.demo.project.nitin.ecommerce.dto.response.CustomerProfileDTO;
import com.demo.project.nitin.ecommerce.dto.response.ResponseDTO;
import com.demo.project.nitin.ecommerce.entity.Address;
import com.demo.project.nitin.ecommerce.entity.Customer;
import com.demo.project.nitin.ecommerce.entity.User;
import com.demo.project.nitin.ecommerce.exception.exceptions.ResourceNotFoundException;
import com.demo.project.nitin.ecommerce.mapper.AddressMapper;
import com.demo.project.nitin.ecommerce.mapper.ResponseMapper;
import com.demo.project.nitin.ecommerce.mapper.UserMapper;
import com.demo.project.nitin.ecommerce.repository.AddressRepo;
import com.demo.project.nitin.ecommerce.service.CustomerService;
import com.demo.project.nitin.ecommerce.service.helper.UserHelperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.demo.project.nitin.ecommerce.utils.CommonUtil.toUUID;
import static com.demo.project.nitin.ecommerce.utils.MappingUtil.mapNonNullUserFields;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final UserHelperService userHelperService;
    private final AddressRepo addressRepo;
    private final ResponseMapper responseMapper;
    private final AddressMapper addressMapper;
    private final UserMapper userMapper;

    @Override
    public ResponseDTO viewProfile(String userName) {
        User user = userHelperService.findByEmail(userName);
        CustomerProfileDTO profileDTO = userMapper.toProfileDto((Customer) user);
        profileDTO.setImage(
                userHelperService.profileImage(user.getId().toString())
        );
        return responseMapper.toResponseDto(HttpStatus.OK,
                MessageKeys.CUSTOMER_PROFILE_VIEW,
                profileDTO);
    }

    @Override
    public ResponseDTO updateProfile(String userName, UpdateCustomerDTO dto) {
        User user = userHelperService.findByEmail(userName);
        userMapper.updateEntityFromDto(dto, (Customer) user);
        log.info("Updating profile for user: '{}'", user.getId());
        userHelperService.updateUser(user);
        Map<String, Object> updatedFields = mapNonNullUserFields(dto);
        return responseMapper.toResponseDto(HttpStatus.OK,
                MessageKeys.CUSTOMER_PROFILE_UPDATED,
                updatedFields
        );
    }

    @Override
    public ResponseDTO addAddress(String userName, AddressDTO addressDTO) {
        User user = userHelperService.findByEmail(userName);
        Address address = addressMapper.toEntity(addressDTO);
        address.setUser(user);
        addressRepo.save(address);
        log.info("Added address '{}' to user '{}'", address.getId(), user.getId());
        return responseMapper.toResponseDto(
                HttpStatus.CREATED, MessageKeys.ADDRESS_ADDED, addressMapper.toResponseDto(address)
        );
    }

    @Override
    public ResponseDTO removeAddress(String userName, String addressId) {
        User user = userHelperService.findByEmail(userName);
        Address address = addressRepo.findById(toUUID(addressId))
                .orElseThrow(() -> {
                    log.error("Address not found for Id: {}", addressId);
                    return new ResourceNotFoundException(MessageKeys.ADDRESS_NOT_FOUND);
                });
        List<Address> addresses = addressRepo.findByUserAndIsDeletedFalse(user);
        if (!addresses.contains(address)) {
            log.error("Address not found for user: {} with addressId: {}", user.getId(), addressId);
            throw new ResourceNotFoundException(MessageKeys.ADDRESS_NOT_FOUND);
        }
        if (addresses.size() == 1) {
            log.error("Attempted to delete the only remaining address for userId={}," +
                    " but at least one address is required.", user.getId());
            throw new IllegalStateException(MessageKeys.AT_LEAST_ONE_ADDRESS_REQUIRED);
        }
        log.info("Removing address '{}' from user '{}'", addressId, user.getId());
        address.setDeleted(true);
        addressRepo.save(address);
        return responseMapper.toResponseDto(HttpStatus.OK, MessageKeys.ADDRESS_DELETED, null);
    }

    @Override
    public ResponseDTO getAddresses(String userName) {
        User user = userHelperService.findByEmail(userName);
        List<Address> addresses = addressRepo.findByUserAndIsDeletedFalse(user);
        log.debug("Fetched {} addresses for user: {}", addresses.size(), user.getId());
        List<AddressResponseDTO> addressDTOs = addresses.stream().map(addressMapper::toResponseDto).toList();
        return responseMapper.toResponseDto(HttpStatus.OK, MessageKeys.ADDRESSES_FETCHED, addressDTOs);
    }
}
