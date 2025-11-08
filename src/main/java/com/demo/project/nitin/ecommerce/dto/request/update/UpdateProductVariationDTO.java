package com.demo.project.nitin.ecommerce.dto.request.update;

import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Getter
@Setter
public class UpdateProductVariationDTO {

    private MultipartFile primaryImage;

    private MultipartFile[] secondaryImages;

    @Min(value = 0, message = MessageKeys.QUANTITY_AVAILABLE_INVALID)
    private int quantityAvailable;

    @Min(value = 0, message = MessageKeys.PRICE_INVALID)
    private double price;

    Boolean isActive;

    private Map<String, String> metadata;
}
