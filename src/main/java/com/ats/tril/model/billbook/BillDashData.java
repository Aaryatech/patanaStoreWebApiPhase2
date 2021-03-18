package com.ats.tril.model.billbook;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BillDashData {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "bill_id")
	private int billId;

	private int vendorId;
	private String vendorName;
	private String billBookNo;
	private String billNo;
	private String billDate;
	private float totalValue;
	private int days;
	private String expiryDate;
	private float paidAmt;

	public int getBillId() {
		return billId;
	}

	public void setBillId(int billId) {
		this.billId = billId;
	}

	public int getVendorId() {
		return vendorId;
	}

	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getBillBookNo() {
		return billBookNo;
	}

	public void setBillBookNo(String billBookNo) {
		this.billBookNo = billBookNo;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	public float getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(float totalValue) {
		this.totalValue = totalValue;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public float getPaidAmt() {
		return paidAmt;
	}

	public void setPaidAmt(float paidAmt) {
		this.paidAmt = paidAmt;
	}

	@Override
	public String toString() {
		return "BillDashData [billId=" + billId + ", vendorId=" + vendorId + ", vendorName=" + vendorName
				+ ", billBookNo=" + billBookNo + ", billNo=" + billNo + ", billDate=" + billDate + ", totalValue="
				+ totalValue + ", days=" + days + ", expiryDate=" + expiryDate + ", paidAmt=" + paidAmt + "]";
	}

}
