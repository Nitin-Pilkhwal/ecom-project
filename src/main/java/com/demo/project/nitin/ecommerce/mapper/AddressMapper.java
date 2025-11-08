package com.demo.project.nitin.ecommerce.mapper;

import com.demo.project.nitin.ecommerce.dto.request.AddressDTO;
import com.demo.project.nitin.ecommerce.dto.response.AddressResponseDTO;
import com.demo.project.nitin.ecommerce.dto.request.update.UpdateAddressDTO;
import com.demo.project.nitin.ecommerce.entity.Address;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    Address toEntity(AddressDTO addressDTO);

    AddressResponseDTO toResponseDto(Address address);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAddressFromDto(UpdateAddressDTO dto, @MappingTarget Address entity);
}
