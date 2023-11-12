package com.woof.promotionactivity.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="promotion_activity")
public class PromotionActivity {
	
	// nullable = false(不可以是空值)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PA_NO" , updatable = false , nullable = false)//促銷活動編號 PK,AI
	private Integer paNo;
	
	@Column(name = "PA_NAME" , nullable = false)				  //促銷活動名稱
	private String paName;
	
	@Column(name = "PA_DISCOUNT" , nullable = false)			  //促銷折扣數
	private BigDecimal paDiscount;
	
	@Column(name = "PA_CONTENT" , nullable = false)				  //促銷活動內容
	private String paContent;
	
	@Column(name = "PA_START" , nullable = false)				  //促銷開始日期
	private Timestamp paStart;
	
	@Column(name = "PA_END" , nullable = false)					  //促銷結束日期
	private Timestamp paEnd;
	
	@Column(name = "PA_STATUS" , nullable = false)				  //促銷活動狀態 0下架 1上架
	private Boolean paStatus;
	
	
	public PromotionActivity() {
    }
	
	public Integer getPaNo() {
		return paNo;
	}
	public void setPaNo(Integer paNo) {
		this.paNo = paNo;
	}
	public String getPaName() {
		return paName;
	}
	public void setPaName(String paName) {
		this.paName = paName;
	}
	public BigDecimal getPaDiscount() {
		return paDiscount;
	}
	public void setPaDiscount(BigDecimal paDiscount) {
		this.paDiscount = paDiscount;
	}
	public String getPaContent() {
		return paContent;
	}
	public void setPaContent(String paContent) {
		this.paContent = paContent;
	}
	public Timestamp getPaStart() {
		return paStart;
	}
	public void setPaStart(Timestamp paStart) {
		this.paStart = paStart;
	}
	public Timestamp getPaEnd() {
		return paEnd;
	}
	public void setPaEnd(Timestamp paEnd) {
		this.paEnd = paEnd;
	}
	public Boolean getPaStatus() {
		return paStatus;
	}
	public void setPaStatus(Boolean paStatus) {
		this.paStatus = paStatus;
	}
	@Override
	public int hashCode() {
		return Objects.hash(paContent, paDiscount, paEnd, paName, paNo, paStart, paStatus);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PromotionActivity other = (PromotionActivity) obj;
		return Objects.equals(paContent, other.paContent) && Objects.equals(paDiscount, other.paDiscount)
				&& Objects.equals(paEnd, other.paEnd) && Objects.equals(paName, other.paName)
				&& Objects.equals(paNo, other.paNo) && Objects.equals(paStart, other.paStart)
				&& Objects.equals(paStatus, other.paStatus);
	}
	@Override
	public String toString() {
		return "PromotionActivity [paNo=" + paNo + ", paName=" + paName + ", paDiscount=" + paDiscount
				+ ", paContent=" + paContent + ", paStart=" + paStart + ", paEnd=" + paEnd + ", paStatus=" + paStatus
				+ "]";
	}
	
	
	

}
