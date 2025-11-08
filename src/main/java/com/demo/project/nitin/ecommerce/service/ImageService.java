package com.demo.project.nitin.ecommerce.service;

import com.demo.project.nitin.ecommerce.constant.enums.ImageType;
import com.demo.project.nitin.ecommerce.dto.response.ResponseDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    ResponseDTO uploadImage(MultipartFile file, ImageType imageType, String name);

    byte[] getProfileImage(String userId);

    byte[] getVariationImage(String variationId);

    ResponseDTO removeVariationImage(String imageId, String name);
}
