package com.demo.project.nitin.ecommerce.constant.enums;

import lombok.Getter;

@Getter
public enum Rating {
    VERY_POOR(0),
    POOR(1),
    NOT_BAD(2),
    GOOD(3),
    WORTH_IT(4),
    EXCELLENT_BUY(5);

    private final int stars;

    Rating(int stars) {
        this.stars = stars;
    }

}
