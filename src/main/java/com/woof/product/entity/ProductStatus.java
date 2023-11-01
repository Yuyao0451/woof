package com.woof.product.entity;

public enum ProductStatus {
    OFF_SHELF(0, "下架中"),
    ON_SHELF(1, "銷售中");

    private final int code;
    private final String displayName;

    ProductStatus(int code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public int getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }
}
