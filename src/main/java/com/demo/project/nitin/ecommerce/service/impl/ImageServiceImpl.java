package com.demo.project.nitin.ecommerce.service.impl;

import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import com.demo.project.nitin.ecommerce.constant.enums.ImageType;
import com.demo.project.nitin.ecommerce.dto.response.ResponseDTO;
import com.demo.project.nitin.ecommerce.entity.Product;
import com.demo.project.nitin.ecommerce.entity.ProductVariation;
import com.demo.project.nitin.ecommerce.entity.Seller;
import com.demo.project.nitin.ecommerce.entity.User;
import com.demo.project.nitin.ecommerce.exception.exceptions.FileException;
import com.demo.project.nitin.ecommerce.exception.exceptions.ResourceNotFoundException;
import com.demo.project.nitin.ecommerce.mapper.ResponseMapper;
import com.demo.project.nitin.ecommerce.service.ImageService;
import com.demo.project.nitin.ecommerce.service.helper.FileHelperService;
import com.demo.project.nitin.ecommerce.service.helper.ProductHelperService;
import com.demo.project.nitin.ecommerce.service.helper.UserHelperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final UserHelperService userHelperService;
    private final FileHelperService fileHelperService;
    private final ResponseMapper responseMapper;
    private final ProductHelperService productHelperService;

    @Override
    public ResponseDTO uploadImage(MultipartFile file, ImageType imageType, String userId) {
        User user = userHelperService.findByEmail(userId);
        String fileName = file.getOriginalFilename();
        return responseMapper.toResponseDto(HttpStatus.CREATED,
                MessageKeys.IMAGE_UPLOADED,
                fileHelperService.uploadImage(file, imageType, fileName,user.getId().toString()));
    }

    @Override
    public byte[] getProfileImage(String userId) {
        User user = userHelperService.findById(userId);
        Path path = Paths.get(fileHelperService.userProfileImagePath(user.getId().toString()));
        if(!Files.exists(path) || !Files.isRegularFile(path)){
            throw new ResourceNotFoundException(MessageKeys.ERROR_IMAGE_NOT_FOUND);
        }
        try {
            return Files.readAllBytes(path.toAbsolutePath());
        } catch (Exception e) {
            throw new FileException(e.getMessage());
        }
    }

    @Override
    public byte[] getVariationImage(String imageId) {
        String variationId = imageId.split("_")[0];
        ProductVariation  productVariation = productHelperService.findById(variationId);
        Product product = productVariation.getProduct();
        try {
            Path path = Paths.get(
                    fileHelperService.getImage(ImageType.PRODUCT_VARIATION, product.getId().toString(), imageId)
                            .getFirst().toString()
            );
            return Files.readAllBytes(path.toAbsolutePath());
        } catch (Exception e) {
            throw new FileException(e.getMessage());
        }
    }

    @Override
    public ResponseDTO removeVariationImage(String imageId, String userName) {
        User user = userHelperService.findById(userName);
        String variationId = imageId.split("_")[0];
        ProductVariation  productVariation = productHelperService.findById(variationId);
        Product product = productVariation.getProduct();
        if(productHelperService.findByIdAndSeller(product.getId().toString(),(Seller) user) == null){
            log.error("Product: {} not found for the seller: {}",product.getId(),user.getId());
            throw new ResourceNotFoundException(MessageKeys.PRODUCT_NOT_FOUND);
        }
        try{
            Path path = Paths.get(
                    fileHelperService.getImage(ImageType.PRODUCT_VARIATION, product.getId().toString(), imageId)
                            .getFirst().toString()
            );
            fileHelperService.deleteFile(path);
        }catch (Exception e){
            log.error("Not able to delete file: {} from storage: {}",imageId,e.getMessage());
            throw new FileException(MessageKeys.FILE_NOT_DELETED);
        }
        return responseMapper.toResponseDto(HttpStatus.OK,MessageKeys.IMAGE_DELETED,null);
    }
}
