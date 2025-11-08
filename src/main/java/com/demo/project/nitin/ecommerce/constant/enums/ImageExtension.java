package com.demo.project.nitin.ecommerce.constant.enums;

public enum ImageExtension {
    PNG("png"),
    JPG("jpg"),
    JPEG("jpeg"),
    BMP("bmp");
    private final String extension;
    ImageExtension(String extension) {
        this.extension = extension;
    }
    public static boolean supported(String extension) {
        for (ImageExtension imageExtension : ImageExtension.values()) {
            if (imageExtension.extension.equals(extension)) {
                return true;
            }
        }
        return false;
    }
}
