package com.woof.product.service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProductDto {
    private Integer prodNo;

    @NotBlank(message = "商品類別不能為空")
    private String prodCatName;

    @NotBlank(message = "商品描述不能為空")
    private String prodContent;

    @NotNull(message = "商品價格不能為空")
    private Integer prodPrice;

    @NotBlank(message = "商品名稱不能為空")
    private String prodName;

    @NotBlank(message = "商品狀態不能為空")
    private String prodStatus;

    private byte[] prodPhoto;

    private Integer promoId;

    private Integer promoPrice;  // 折扣後價格

    private boolean isPromotion;

    public Integer getProdNo() {
        return prodNo;
    }

    public void setProdNo(Integer prodNo) {
        this.prodNo = prodNo;
    }

    public String getProdCatName() {
        return prodCatName;
    }

    public void setProdCatName(String prodCatName) {
        this.prodCatName = prodCatName;
    }

    public String getProdContent() {
        return prodContent;
    }

    public void setProdContent(String prodContent) {
        this.prodContent = prodContent;
    }

    public Integer getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(Integer prodPrice) {
        this.prodPrice = prodPrice;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdStatus() {
        return prodStatus;
    }

    public void setProdStatus(String prodStatus) {
        this.prodStatus = prodStatus;
    }

    public byte[] getProdPhoto() {
        return prodPhoto;
    }

    public void setProdPhoto(byte[] prodPhoto) {
        this.prodPhoto = prodPhoto;
    }

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }

    public Integer getPromoPrice() {
        return promoPrice;
    }

    public void setPromoPrice(Integer promoPrice) {
        this.promoPrice = promoPrice;
    }

    public boolean isPromotion() {
        return isPromotion;
    }

    public void setIsPromotion(boolean isPromotion) {
        this.isPromotion = isPromotion;
    }

}
