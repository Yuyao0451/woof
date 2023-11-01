package com.woof.product.entity;

import com.woof.productphoto.entity.ProductPhoto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

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


	@Column(name="PROD_CONTENT", length = 300, nullable = false) //商品描述
	private String prodContent;

	@Column(name="PROD_PRICE", nullable = false) //商品價格
	private Integer prodPrice;

	@Column(name="PROD_NAME", length = 30, nullable = false) //商品名稱
	private String prodName;

	@Column(name="PROD_STATUS", nullable = false) //商品狀態 0:下架 1:上架
	@Enumerated(EnumType.ORDINAL)
	private ProductStatus prodStatus;

	@OneToMany(mappedBy = "prodNo", fetch = FetchType.LAZY)
	private List<ProductPhoto> productPhotos;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Product product = (Product) o;
		return Objects.equals(prodNo, product.prodNo) && prodCatName == product.prodCatName && Objects.equals(prodContent, product.prodContent) && Objects.equals(prodPrice, product.prodPrice) && Objects.equals(prodName, product.prodName) && prodStatus == product.prodStatus;
	}

	@Override
	public int hashCode() {
		return Objects.hash(prodNo, prodCatName, prodContent, prodPrice, prodName, prodStatus);
	}
}
