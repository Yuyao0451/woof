package com.woof.product.service;

import java.util.List;

public class ProductDto {
    private Integer prodNo;
    private String prodCatName;
    private String prodContent;
    private Integer prodPrice;
    private String prodName;
    private String prodStatus;
    private List<byte[]> productPhotos;
    private List<String> base64ProductPhotos;


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

    public List<byte[]> getProductPhotos() {
        return productPhotos;
    }

    public void setProductPhotos(List<byte[]> productPhotos) {
        this.productPhotos = productPhotos;
    }

    public List<String> getBase64ProductPhotos() {
        return base64ProductPhotos;
    }

    public void setBase64ProductPhotos(List<String> base64ProductPhotos) {
        this.base64ProductPhotos = base64ProductPhotos;
    }
}
