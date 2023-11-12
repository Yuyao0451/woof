package com.woof.product.entity;


import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="product")
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="PROD_NO", updatable = false) //商品編號
	private Integer prodNo;

	@Column(name="PROD_CAT_NAME", nullable = false) //商品類別名稱
	@Enumerated(EnumType.STRING)
	private ProductCategory prodCatName;

	@NotBlank(message = "商品描述不能為空")
	@Column(name="PROD_CONTENT", length = 300, nullable = false) //商品描述
	private String prodContent;

	@NotNull(message = "商品價格為必填")
	@Column(name="PROD_PRICE", nullable = false) //商品價格
	private Integer prodPrice;

	@NotBlank(message = "商品名稱為必填")
	@Column(name="PROD_NAME", length = 30, nullable = false) //商品名稱
	private String prodName;

	@Column(name="PROD_STATUS", nullable = false) //商品狀態 0:下架 1:上架
	@Enumerated(EnumType.ORDINAL)
	private ProductStatus prodStatus;

	@Lob
	@Column(name="PROD_PHOTO") //商品照片
	private byte[] prodPhoto;

	@Column(name="PROMO_ID") // 用於存儲促銷活動ID
	private Integer promoId;

	public Integer getProdNo() {
		return prodNo;
	}

	public void setProdNo(Integer prodNo) {
		this.prodNo = prodNo;
	}

	public ProductCategory getProdCatName() {
		return prodCatName;
	}

	public void setProdCatName(ProductCategory prodCatName) {
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

	public ProductStatus getProdStatus() {
		return prodStatus;
	}

	public void setProdStatus(ProductStatus prodStatus) {
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
}
