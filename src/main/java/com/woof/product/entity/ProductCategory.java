package com.woof.product.entity;

public enum ProductCategory {
    DOG_FOOD("犬用主食"),
    CAT_FOOD("貓用主食"),
    PET_HEALTHCARE("毛孩保健"),
    PET_SNACKS("毛孩零食"),
    PET_SUPPLIES("毛孩用品"),
    ENVIRONMENT_CLEANING("環境清潔"),
    BEAUTY_TOOLS("美容用具"),
    OUTDOOR_SUPPLIES("外出用品");

    private final String displayName;

    ProductCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
