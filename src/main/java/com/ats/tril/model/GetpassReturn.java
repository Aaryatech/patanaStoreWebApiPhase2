package com.ats.tril.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "t_getpass_return")
public class GetpassReturn {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int returnId;
	private String returnNo;

	private int gpId;
	private String gpNo;
	private int vendorId;

	private String gpReturnDate;
	private String gpRemark;
	private String gpRemark1;
	private int status;
	private int isUsed;

	@Transient
	List<GetpassReturnDetail> getpassReturnDetailList;

	public int getReturnId() {
		return returnId;
	}

	public void setReturnId(int returnId) {
		this.returnId = returnId;
	}

	public String getReturnNo() {
		return returnNo;
	}

	public void setReturnNo(String returnNo) {
		this.returnNo = returnNo;
	}

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

	public int getVendorId() {
		return vendorId;
	}

	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

	public String getGpRemark() {
		return gpRemark;
	}

	public String getGpReturnDate() {
		return gpReturnDate;
	}

	public void setGpReturnDate(String gpReturnDate) {
		this.gpReturnDate = gpReturnDate;
	}

	public void setGpRemark(String gpRemark) {
		this.gpRemark = gpRemark;
	}

	public String getGpRemark1() {
		return gpRemark1;
	}

	public void setGpRemark1(String gpRemark1) {
		this.gpRemark1 = gpRemark1;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(int isUsed) {
		this.isUsed = isUsed;
	}

	public List<GetpassReturnDetail> getGetpassReturnDetailList() {
		return getpassReturnDetailList;
	}

	public void setGetpassReturnDetailList(List<GetpassReturnDetail> getpassReturnDetailList) {
		this.getpassReturnDetailList = getpassReturnDetailList;
	}

	@Override
	public String toString() {
		return "GetpassReturn [returnId=" + returnId + ", returnNo=" + returnNo + ", gpId=" + gpId + ", gpNo=" + gpNo
				+ ", vendorId=" + vendorId + ", gpReturnDate=" + gpReturnDate + ", gpRemark=" + gpRemark
				+ ", gpRemark1=" + gpRemark1 + ", status=" + status + ", isUsed=" + isUsed
				+ ", getpassReturnDetailList=" + getpassReturnDetailList + "]";
	}

}
