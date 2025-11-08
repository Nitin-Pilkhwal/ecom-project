package com.demo.project.nitin.ecommerce.utils;

import com.demo.project.nitin.ecommerce.constant.Constants;
import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import com.demo.project.nitin.ecommerce.constant.enums.ImageExtension;
import com.demo.project.nitin.ecommerce.exception.exceptions.DuplicateFieldException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiPredicate;

@Slf4j
public class CommonUtil {

    private CommonUtil() {}

    public static void matchPassword(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            log.error("Password and Confirm Password do not match {}, {}", password, confirmPassword);
            throw new IllegalArgumentException(MessageKeys.PASSWORD_MISMATCH);
        }
    }

    public static boolean matchMetadata(JsonNode metadata, Map<String, String> map) {
        if (metadata == null || metadata.isEmpty() || map == null || map.isEmpty()) {
            return true;
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String expectedValue = entry.getValue();
            JsonNode node = metadata.get(key);
            if (node == null || node.isNull()) {
                return false;
            }
            String actualValue = node.asText();
            if (!expectedValue.equals(actualValue)) {
                return false;
            }
        }
        return true;
    }

    public static <T> void checkDuplicate(List<T> entities, String errorMessage) {
        if (entities != null && !entities.isEmpty()) {
            log.error(errorMessage);
            throw new DuplicateFieldException(errorMessage);
        }
    }

    public static <T> void checkDuplicate(Optional<T> entity, String errorMessage) {
        entity.ifPresent(user -> {
            throw new DuplicateFieldException(errorMessage);
        });
    }

    public static Pageable getEffectivePageable(Pageable pageable) {
        int page = (pageable.getPageNumber() < 0) ? Constants.API.DEFAULT_PAGE_OFFSET : pageable.getPageNumber();
        int size = (pageable.getPageSize() <= 0) ? Constants.API.DEFAULT_PAGE_SIZE : pageable.getPageSize();
        Sort sortBy = (pageable.getSort().isUnsorted() ?
                Sort.by(Constants.API.DEFAULT_SORT_FIELD).ascending() : pageable.getSort());
        return PageRequest.of(page, size, sortBy);
    }

    public static boolean validateImageUpload(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            log.error("Uploaded file is null or empty");
            throw new IllegalArgumentException(MessageKeys.ERROR_FILE_EMPTY);
        }
        String uploadedFileName = file.getOriginalFilename();
        String extension;
        if (uploadedFileName == null) {
            log.error("Uploaded file name is null");
            throw new IllegalArgumentException(MessageKeys.ERROR_FILE_NAME_NULL);
        }
        int index = uploadedFileName.lastIndexOf('.');
        if (index > 0 && index < uploadedFileName.length() - 1) {
            extension = uploadedFileName.substring(index + 1);
            if (ImageExtension.supported(extension)) {
                return true;
            }
            log.error("Unsupported file extension: {}", extension);
            throw new IllegalArgumentException(MessageKeys.ERROR_EXTENSION_UNSUPPORTED);
        }
        return false;
    }

    public static void checkUnique(String value, UUID id,
                             BiPredicate<String, UUID> biPredicate,
                             String errorMessage) {
        if (value != null && biPredicate.test(value, id)) {
            log.error("{} Value: {}, Id: {}", errorMessage, value, id);
            throw new DuplicateFieldException(errorMessage);
        }
    }

    public static UUID toUUID(String value){
        try{
            return UUID.fromString(value);
        } catch (IllegalArgumentException e){
            log.error("Invalid id: {}", value);
            throw new IllegalArgumentException(MessageKeys.INVALID_UUID);
        }
    }

    public static String fillValues(String template, String... values) {
        if (template == null || values == null) {
            return template;
        }
        String result = template;
        for (int i = 0; i < values.length; i++) {
            String placeholder = "{" + (i + 1) + "}";
            result = result.replace(placeholder, values[i]);
        }
        return result;
    }

}
