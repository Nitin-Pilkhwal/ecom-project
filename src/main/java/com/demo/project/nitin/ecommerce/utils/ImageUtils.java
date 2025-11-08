package com.demo.project.nitin.ecommerce.utils;

import com.demo.project.nitin.ecommerce.configuration.ApplicationConfig;
import com.demo.project.nitin.ecommerce.constant.enums.ImageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImageUtils {

    private final ApplicationConfig applicationConfig;

    public Path resolveFilePath(ImageType imageType, String fileName, String... id) {
        return switch (imageType) {
            case USER -> Paths.get(
                    applicationConfig.getImageProperties().getBaseDir(),
                            applicationConfig.getImageProperties().getUsers()
                    )
                    .resolve(fileName);
            case PRODUCT -> Paths.get(
                    applicationConfig.getImageProperties().getBaseDir(),
                            applicationConfig.getImageProperties().getProducts()
                    )
                    .resolve(id[0]).resolve(fileName);
            case PRODUCT_VARIATION -> Paths.get(
                    applicationConfig.getImageProperties().getBaseDir(),
                            applicationConfig.getImageProperties().getProducts()
                    )
                    .resolve(id[0])
                    .resolve("variation")
                    .resolve(id[1])
                    .resolve(fileName);
        };
    }

    public Path resolveDirPath(ImageType imageType, String... id) {
        return switch (imageType) {
            case USER -> Paths.get(
                    applicationConfig.getImageProperties().getBaseDir(),
                    applicationConfig.getImageProperties().getUsers()
            );
            case PRODUCT -> Paths.get(
                    applicationConfig.getImageProperties().getBaseDir(),
                            applicationConfig.getImageProperties().getProducts()
                    )
                    .resolve(id[0]);
            case PRODUCT_VARIATION -> Paths.get(
                    applicationConfig.getImageProperties().getBaseDir(),
                            applicationConfig.getImageProperties().getProducts()
                    )
                    .resolve(id[0])
                    .resolve("variation")
                    .resolve(id[1].split("_")[0]);
        };
    }

    public String getImageName(MultipartFile file, String... id) {
        String uploadedFileName = file.getOriginalFilename();
        int index = uploadedFileName.lastIndexOf('.');
        String extension = uploadedFileName.substring(index + 1);
        return id[id.length - 1] + "." + extension;
    }
}