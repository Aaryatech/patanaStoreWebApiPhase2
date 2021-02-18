package com.ats.tril.model.report;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class GatepassReport {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int gpId;
	private String gpNo;
	private int gpVendor;

	private int gpType;
	private Date gpReturnDate;
	private int gpStatus;
	private int isUsed;

	private String remark1;
	private String remark2;
	private String sendingWith;
	private int isStockable;
	private int forRepair;
	private Date gpDate;
	private String vendorName;
	private String vendorCode;
	private String vendorAdd1;
	@Transient
	List<GatepassReportDetail> gatepassReportDetailList;

	public int getGpId() {
		return gpId;
	}

	public void setGpId(int gpId) {
		this.gpId = gpId;
	}

	public String getGpNo() {
		return gpNo;
	}

	public void setGpNo(String gpNo) {
		this.gpNo = gpNo;
	}

	public int getGpVendor() {
		return gpVendor;
	}

	public void setGpVendor(int gpVendor) {
		this.gpVendor = gpVendor;
	}

	public int getGpType() {
		return gpType;
	}

	public void setGpType(int gpType) {
		this.gpType = gpType;
	}

	@JsonFormat(locale = "hi", timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy")
	public Date getGpReturnDate() {
		return gpReturnDate;
	}

	public void setGpReturnDate(Date gpReturnDate) {
		this.gpReturnDate = gpReturnDate;
	}

	public int getGpStatus() {
		return gpStatus;
	}

	public void setGpStatus(int gpStatus) {
		this.gpStatus = gpStatus;
	}

	public int getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(int isUsed) {
		this.isUsed = isUsed;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getSendingWith() {
		return sendingWith;
	}

	public void setSendingWith(String sendingWith) {
		this.sendingWith = sendingWith;
	}

	public int getIsStockable() {
		return isStockable;
	}

	public void setIsStockable(int isStockable) {
		this.isStockable = isStockable;
	}

	public int getForRepair() {
		return forRepair;
	}

	public void setForRepair(int forRepair) {
		this.forRepair = forRepair;
	}

	@JsonFormat(locale = "hi", timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy")
	public Date getGpDate() {
		return gpDate;
	}

	public void setGpDate(Date gpDate) {
		this.gpDate = gpDate;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public String getVendorAdd1() {
		return vendorAdd1;
	}

	public void setVendorAdd1(String vendorAdd1) {
		this.vendorAdd1 = vendorAdd1;
	}

	public List<GatepassReportDetail> getGatepassReportDetailList() {
		return gatepassReportDetailList;
	}

	public void setGatepassReportDetailList(List<GatepassReportDetail> gatepassReportDetailList) {
		this.gatepassReportDetailList = gatepassReportDetailList;
	}

	@Override
	public String toString() {
		return "GatepassReport [gpId=" + gpId + ", gpNo=" + gpNo + ", gpVendor=" + gpVendor + ", gpType=" + gpType
				+ ", gpReturnDate=" + gpReturnDate + ", gpStatus=" + gpStatus + ", isUsed=" + isUsed + ", remark1="
				+ remark1 + ", remark2=" + remark2 + ", sendingWith=" + sendingWith + ", isStockable=" + isStockable
				+ ", forRepair=" + forRepair + ", gpDate=" + gpDate + ", vendorName=" + vendorName + ", vendorCode="
				+ vendorCode + ", vendorAdd1=" + vendorAdd1 + ", gatepassReportDetailList=" + gatepassReportDetailList
				+ "]";
	}

}
