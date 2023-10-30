package com.yuyao.woof.promotionproduct.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "promotion_product")
@IdClass(PromotionProductVO.CompositeDetail.class)
public class PromotionProductVO implements Serializable {
	
	@Id
	@Column(name = "PROD_NO" , nullable = false)
	private Integer prodNo;
	
	@Id
	@Column(name = "PA_NO" , nullable = false)
	private Integer paNo;
	
	public CompositeDetail getCompositeKey() {
		return new CompositeDetail(prodNo, paNo);
	}
	
	public void setCompositeKey(CompositeDetail key) {
		this.prodNo = key.getProdNo();
		this.paNo = key.getPaNo();
	}

	public PromotionProductVO() {
	}

	public Integer getProdNo() {
		return prodNo;
	}

	public void setProdNo(Integer prodNo) {
		this.prodNo = prodNo;
	}

	public Integer getPaNo() {
		return paNo;
	}

	public void setPaNo(Integer paNo) {
		this.paNo = paNo;
	}
	
	static class CompositeDetail implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private Integer prodNo;
		private Integer paNo;
		
		public CompositeDetail() {
			super();
		}

		public CompositeDetail(Integer prodNo, Integer paNo) {
			super();
			this.prodNo = prodNo;
			this.paNo = paNo;
		}

		public Integer getProdNo() {
			return prodNo;
		}

		public void setProdNo(Integer prodNo) {
			this.prodNo = prodNo;
		}

		public Integer getPaNo() {
			return paNo;
		}

		public void setPaNo(Integer paNo) {
			this.paNo = paNo;
		}

		@Override
		public int hashCode() {
			return Objects.hash(paNo, prodNo);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			CompositeDetail other = (CompositeDetail) obj;
			return Objects.equals(paNo, other.paNo) && Objects.equals(prodNo, other.prodNo);
		}

		@Override
		public String toString() {
			return "CompositeDetail [prodNo=" + prodNo + ", paNo=" + paNo + "]";
		}

	}

}
