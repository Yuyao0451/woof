package com.woof.returnlist.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name="return_list")
public class ReturnList implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="RE_EXCH_ID", updatable = false)		//退貨編號
	private Integer reExchId;

    @Column(name="SHOP_ORDER_NO", nullable=false)	//商城訂單編號
	private Integer shopOrderNo;

    @Column(name="PROD_NO", nullable=false)			//商品編號
	private Integer prodNo;

    @Column(name="RE_REASON")		//退貨原因
	private String reReason;

    @Column(name="RE_STATUS", nullable=false, columnDefinition = "TINYINT")		//退貨狀態 0:待處理 1:已接受 2:已拒絕
	private Integer reStatus;

    @Column(name="RE_DATE", nullable=false)			//退貨申請日期
	private Timestamp reDate;

    @Column(name="PROC_DATE")		//退貨處理日期
	private Timestamp procDate;

    @Column(name="REPAY_STATUS", nullable=false, columnDefinition = "TINYINT")	//退款狀態 0:未退款 1:退款中 2:已退款
	private Integer repayStatus;

    @Column(name="RED_AMOUNT", nullable=false)		//退貨數量
	private Integer redAmount;

    @Column(name="RE_PAY_AMOUNT")	//退款金額 Default: 0
	private Integer rePayAmount;

	public ReturnList() {
	}

	public Integer getReExchId() {
		return reExchId;
	}

	public void setReExchId(Integer reExchId) {
		this.reExchId = reExchId;
	}

	public Integer getShopOrderNo() {
		return shopOrderNo;
	}

	public void setShopOrderNo(Integer shopOrderNo) {
		this.shopOrderNo = shopOrderNo;
	}

	public Integer getProdNo() {
		return prodNo;
	}

	public void setProdNo(Integer prodNo) {
		this.prodNo = prodNo;
	}

	public String getReReason() {
		return reReason;
	}

	public void setReReason(String reReason) {
		this.reReason = reReason;
	}

	public Integer getReStatus() {
		return reStatus;
	}

	public void setReStatus(Integer reStatus) {
		this.reStatus = reStatus;
	}

	public Timestamp getReDate() {
		return reDate;
	}

	public void setReDate(Timestamp reDate) {
		this.reDate = reDate;
	}

	public Timestamp getProcDate() {
		return procDate;
	}

	public void setProcDate(Timestamp procDate) {
		this.procDate = procDate;
	}

	public Integer getRepayStatus() {
		return repayStatus;
	}

	public void setRepayStatus(Integer repayStatus) {
		this.repayStatus = repayStatus;
	}

	public Integer getRedAmount() {
		return redAmount;
	}

	public void setRedAmount(Integer redAmount) {
		this.redAmount = redAmount;
	}

	public Integer getRePayAmount() {
		return rePayAmount;
	}

	public void setRePayAmount(Integer rePayAmount) {
		this.rePayAmount = rePayAmount;
	}

	@Override
	public int hashCode() {
		return Objects.hash(procDate, prodNo, reDate, reExchId, rePayAmount, reReason, reStatus, redAmount, repayStatus,
				shopOrderNo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReturnList other = (ReturnList) obj;
		return Objects.equals(procDate, other.procDate) && Objects.equals(prodNo, other.prodNo)
				&& Objects.equals(reDate, other.reDate) && Objects.equals(reExchId, other.reExchId)
				&& Objects.equals(rePayAmount, other.rePayAmount) && Objects.equals(reReason, other.reReason)
				&& Objects.equals(reStatus, other.reStatus) && Objects.equals(redAmount, other.redAmount)
				&& Objects.equals(repayStatus, other.repayStatus) && Objects.equals(shopOrderNo, other.shopOrderNo);
	}

	@Override
	public String toString() {
		return "ReturnListVO [reExchId=" + reExchId + ", shopOrderNo=" + shopOrderNo + ", prodNo=" + prodNo
				+ ", reReason=" + reReason + ", reStatus=" + reStatus + ", reDate=" + reDate + ", procDate=" + procDate
				+ ", repayStatus=" + repayStatus + ", redAmount=" + redAmount + ", rePayAmount=" + rePayAmount + "]";
	}
}


