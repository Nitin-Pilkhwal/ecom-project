package com.demo.project.nitin.ecommerce.mapper;

import com.demo.project.nitin.ecommerce.dto.request.CustomerRequestDTO;
import com.demo.project.nitin.ecommerce.dto.request.SellerRequestDTO;
import com.demo.project.nitin.ecommerce.dto.request.update.UpdateCustomerDTO;
import com.demo.project.nitin.ecommerce.dto.request.update.UpdateSellerDTO;
import com.demo.project.nitin.ecommerce.dto.response.CustomerProfileDTO;
import com.demo.project.nitin.ecommerce.dto.response.CustomerResponseDTO;
import com.demo.project.nitin.ecommerce.dto.response.SellerProfileDTO;
import com.demo.project.nitin.ecommerce.dto.response.SellerResponseDTO;
import com.demo.project.nitin.ecommerce.entity.Address;
import com.demo.project.nitin.ecommerce.entity.Customer;
import com.demo.project.nitin.ecommerce.entity.Seller;
import org.mapstruct.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Mapper(componentModel = "spring",uses =  {AddressMapper.class})
public interface UserMapper {

    @Mapping(target = "password",expression = "java(passwordEncoder.encode(customerRequestDTO.getPassword()))")
    Customer toEntity(CustomerRequestDTO customerRequestDTO, PasswordEncoder passwordEncoder);

    @Mapping(target = "password",expression = "java(passwordEncoder.encode(sellerRequestDTO.getPassword()))")
    Seller toEntity(SellerRequestDTO sellerRequestDTO, PasswordEncoder passwordEncoder);

    @Mapping(target = "address", source = "addresses")
    CustomerResponseDTO toDto(Customer customer);

    @Mapping(target = "companyAddress", source = "addresses")
    SellerResponseDTO toDto(Seller seller);

    CustomerProfileDTO toProfileDto(Customer customer);

    @Mapping(target = "companyAddress", source = "addresses")
    SellerProfileDTO toProfileDto(Seller seller);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UpdateSellerDTO dto, @MappingTarget Seller entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UpdateCustomerDTO dto, @MappingTarget Customer entity);

    default Address mapAddress(List<Address> addresses) {
        if (addresses == null || addresses.isEmpty()) {
            return null;
        }
        return addresses.getFirst();
    }
}
