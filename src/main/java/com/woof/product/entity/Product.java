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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Product product = (Product) o;
		return Objects.equals(prodNo, product.prodNo) && prodCatName == product.prodCatName && Objects.equals(prodContent, product.prodContent) && Objects.equals(prodPrice, product.prodPrice) && Objects.equals(prodName, product.prodName) && prodStatus == product.prodStatus && Arrays.equals(prodPhoto, product.prodPhoto);
	}

	@Override
	public int hashCode() {
		int result = Objects.hash(prodNo, prodCatName, prodContent, prodPrice, prodName, prodStatus);
		result = 31 * result + Arrays.hashCode(prodPhoto);
		return result;
	}

	@Override
	public String toString() {
		String photoDesc = (prodPhoto != null) ? "Photo Size: " + prodPhoto.length + " bytes" : "No Photo";
		return "Product{" +
				"prodNo=" + prodNo +
				", prodCatName=" + prodCatName +
				", prodContent='" + prodContent + '\'' +
				", prodPrice=" + prodPrice +
				", prodName='" + prodName + '\'' +
				", prodStatus=" + prodStatus +
				", " + photoDesc +
				'}';
	}//照片是二進制 怕會印出大量數據 更改成只印出長度
}
