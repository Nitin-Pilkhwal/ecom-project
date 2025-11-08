package com.demo.project.nitin.ecommerce.service.helper;

import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import com.demo.project.nitin.ecommerce.constant.enums.ImageType;
import com.demo.project.nitin.ecommerce.dto.response.ProductVariationListResponseDTO;
import com.demo.project.nitin.ecommerce.dto.response.ProductWithVariationResponseDTO;
import com.demo.project.nitin.ecommerce.exception.exceptions.FileException;
import com.demo.project.nitin.ecommerce.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static com.demo.project.nitin.ecommerce.utils.CommonUtil.validateImageUpload;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileHelperService {

    private final ImageUtils imageUtils;

    public String uploadImage(MultipartFile file, ImageType imageType, String... id) {
        if (validateImageUpload(file)) {
            String imageName = imageUtils.getImageName(file, id);
            log.info("Image name {}",imageName);
            Path finalPath = imageUtils.resolveFilePath(imageType, imageName, id);
            log.info("Uploading file {} to path {}", finalPath, id);
            try {
                Files.createDirectories(finalPath.getParent());
                List<Path> image = getImage(imageType, id);
                if (!image.isEmpty() && ImageType.USER.equals(imageType)) {
                    image.forEach(this::deleteFile);
                }
                file.transferTo(finalPath.toFile());
                return imageName;
            } catch (IOException e) {
                log.error("Failed to save image to path :{} ,  {}", id, e.getMessage());
                throw new FileException(MessageKeys.ERROR_IMAGE_SAVE);
            }
        }
        throw new FileException(MessageKeys.ERROR_IMAGE_SAVE);
    }

    public void fillSecondaryImages(String productId, ProductWithVariationResponseDTO listResponseDTO) {
        if (listResponseDTO.getVariations() != null) {
            for (ProductVariationListResponseDTO variationDTO : listResponseDTO.getVariations()) {
                fillSecondaryImagesInProductVariationListResponse(productId, variationDTO);
            }
        }
    }

    public void fillSecondaryImagesInProductVariationListResponse(
            String productId,
            ProductVariationListResponseDTO variationDTO) {
        List<Path> images =getImage(ImageType.PRODUCT_VARIATION, productId, variationDTO.getId().toString());
        List<String> secondaryImages = images.stream()
                .map(path -> path.getFileName().toString())
                .filter(name -> !name.contains("PRIMARY"))
                .toList();
        variationDTO.setSecondaryImages(secondaryImages);
    }

    public void deleteFile(Path path) {
        try {
            Files.deleteIfExists(path);
            log.info("Deleted image {} from path {}", path.getFileName(), path);
        } catch (IOException e) {
            log.error("Failed to delete image {} from path {}", path.getFileName(), path, e);
            throw new FileException(MessageKeys.FILE_NOT_DELETED);
        }
    }

    public String userProfileImagePath(String id) {
        List<Path> image = getImage(ImageType.USER, id);
        if (!image.isEmpty()) {
            return image.getFirst().toString();
        }
        return null;
    }

    public List<Path> getImage(ImageType imageType, String... id) {
        String imageName = id[id.length - 1];
        log.info("Getting image {} from path {}", imageName, id);
        Path searchPath = imageUtils.resolveDirPath(imageType, id);
        log.info("Searching image {} from path {}", imageName, searchPath);
        try (Stream<Path> files = Files.list(searchPath)) {
            return files.filter(Files::isRegularFile)
                    .filter(f -> f
                            .getFileName()
                            .toString()
                            .startsWith(imageName)
                    )
                    .toList();
        } catch (IOException e) {
            log.error("Failed to find the image to path :{},{}", id, e.getMessage());
            throw new FileException(MessageKeys.ERROR_IMAGE_NOT_FOUND);
        }
    }

    public String uploadVariationImage(
            MultipartFile file,
            String productId,
            String variationId,
            boolean isPrimary,
            Integer secondaryIndex
    ) {
        if (!validateImageUpload(file)) {
            throw new FileException(MessageKeys.ERROR_IMAGE_SAVE);
        }
        try {
            String uploadedFileName = file.getOriginalFilename();
            int index = uploadedFileName.lastIndexOf('.');
            String extension = uploadedFileName.substring(index + 1);
            String finalFileName;
            if (isPrimary) {
                finalFileName = variationId + "_PRIMARY." + extension;
            } else {
                finalFileName = variationId + "_" + secondaryIndex + "." + extension;
            }
            Path finalPath = imageUtils.resolveFilePath(
                    ImageType.PRODUCT_VARIATION,
                    finalFileName,
                    productId,
                    variationId
            );
            Files.createDirectories(finalPath.getParent());
            file.transferTo(finalPath.toFile());
            log.info("Uploaded {} image -> {}", (isPrimary ? "primary" : "secondary"), finalPath);
            return finalFileName;
        } catch (IOException e) {
            log.error("Failed to save image for variation {} -> {}", variationId, e.getMessage());
            throw new FileException(MessageKeys.ERROR_IMAGE_SAVE);
        }
    }

    public String addPrimaryImage(MultipartFile primaryImage, String productId, String variationId) {
        if(primaryImage != null) {
            return uploadVariationImage(primaryImage, productId, variationId, true, null);
        }
        return null;
    }

    public void addSecondaryImages(
            MultipartFile[] secondaryImages,
            String productId,
            String variationId
    ) {
        if (secondaryImages != null) {
            List<Path> images = getImage(ImageType.PRODUCT_VARIATION,productId,variationId);
            int counter = images.size();
            for (MultipartFile file : secondaryImages) {
                uploadVariationImage(file, productId,variationId, false, counter);
                counter++;
            }
        }
    }
}