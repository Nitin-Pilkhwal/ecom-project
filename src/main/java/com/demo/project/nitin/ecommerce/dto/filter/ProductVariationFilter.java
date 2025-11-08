package com.demo.project.nitin.ecommerce.dto.filter;

import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductVariationFilter {

    @DecimalMin(value = "0.0", message = MessageKeys.VARIATION_MIN_PRICE)
    private Double minPrice;

    @DecimalMin(value = "0.0", message = MessageKeys.VARIATION_MAX_PRICE)
    private Double maxPrice;

    @Min(value = 0, message = MessageKeys.VARIATION_MIN_QUANTITY)
    private Integer minQuantity;
}
