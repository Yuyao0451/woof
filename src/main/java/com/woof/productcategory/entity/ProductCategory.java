package com.woof.productcategory.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name="product_category")
public class ProductCategory implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="PROD_CAT_NO", updatable = false) 	 //商品類別編號
	private Integer prodCatNo;

    @Column(name="PROD_CAT_NAME", nullable=false) 	 //商品類別名稱
	private String prodCatName;

    @Column(name="PROD_CAT_CONTENT") //商品類別描述
	private String prodCatContent;


	public ProductCategory() {
	}

	public Integer getProdCatNo() {
		return prodCatNo;
	}

	public void setProdCatNo(Integer prodCatNo) {
		this.prodCatNo = prodCatNo;
	}

	public String getProdCatName() {
		return prodCatName;
	}

	public void setProdCatName(String prodCatName) {
		this.prodCatName = prodCatName;
	}

	public String getProdCatContent() {
		return prodCatContent;
	}

	public void setProdCatContent(String prodCatContent) {
		this.prodCatContent = prodCatContent;
	}

	@Override
	public int hashCode() {
		return Objects.hash(prodCatContent, prodCatName, prodCatNo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductCategory other = (ProductCategory) obj;
		return Objects.equals(prodCatContent, other.prodCatContent) && Objects.equals(prodCatName, other.prodCatName)
				&& Objects.equals(prodCatNo, other.prodCatNo);
	}

	@Override
	public String toString() {
		return "ProductCategoryVO [prodCatNo=" + prodCatNo + ", prodCatName=" + prodCatName + ", prodCatContent="
				+ prodCatContent + "]";
	}
}


