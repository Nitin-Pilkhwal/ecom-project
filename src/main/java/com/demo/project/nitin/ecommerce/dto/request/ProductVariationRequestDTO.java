package com.demo.project.nitin.ecommerce.dto.request;

import com.demo.project.nitin.ecommerce.constant.Constants;
import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Getter
@Setter
public class ProductVariationRequestDTO {

    @NotNull(message = "Product id is required")
    @Pattern(regexp = Constants.Regex.ID,message = MessageKeys.INVALID_ID)
    private String productId;

    @NotNull(message = MessageKeys.PRIMARY_IMAGE_REQUIRED)
    private MultipartFile primaryImage;

    private MultipartFile[] secondaryImages;

    @Min(value = 0, message = MessageKeys.QUANTITY_AVAILABLE_INVALID)
    private int quantityAvailable;

    @Min(value = 0, message = MessageKeys.PRICE_INVALID)
    private double price;

    @NotNull(message = MessageKeys.METADATA_REQUIRED)
    private Map<String, String> metadata;
}
