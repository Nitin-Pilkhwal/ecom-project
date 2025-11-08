package com.demo.project.nitin.ecommerce.mapper;

import com.demo.project.nitin.ecommerce.dto.response.ResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.http.HttpStatus;

@Mapper(componentModel = "spring")
public interface ResponseMapper {

    @Mapping(target = "responseCode", expression = "java(String.valueOf(status.value()))")
    @Mapping(target = "responseMessage", source = "message")
    @Mapping(target = "responseData", source = "data")
    ResponseDTO toResponseDto(HttpStatus status, String message, Object data);
}
