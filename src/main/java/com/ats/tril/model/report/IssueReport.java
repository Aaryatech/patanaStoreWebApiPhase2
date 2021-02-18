package com.ats.tril.model.report;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class IssueReport {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int issueId;

	private String issueNo;

	private int itemCategory;

	private Date issueDate;

	private int deleteStatus;

	private int status;
	
	private int deptId;
	
	private int subDeptId;
	
	private String deptCode; 
	
	private String subDeptCode;
	
	private String issueSlipNo;

	@Transient
	List<IssueReportDetail> issueReportDetailList;

	public int getIssueId() {
		return issueId;
	}

	public void setIssueId(int issueId) {
		this.issueId = issueId;
	}

	public String getIssueNo() {
		return issueNo;
	}

	public void setIssueNo(String issueNo) {
		this.issueNo = issueNo;
	}

	public int getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(int itemCategory) {
		this.itemCategory = itemCategory;
	}

	@JsonFormat(locale = "hi", timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy")

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public int getDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(int deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<IssueReportDetail> getIssueReportDetailList() {
		return issueReportDetailList;
	}

	public void setIssueReportDetailList(List<IssueReportDetail> issueReportDetailList) {
		this.issueReportDetailList = issueReportDetailList;
	}

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public int getSubDeptId() {
		return subDeptId;
	}

	public void setSubDeptId(int subDeptId) {
		this.subDeptId = subDeptId;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getSubDeptCode() {
		return subDeptCode;
	}

	public void setSubDeptCode(String subDeptCode) {
		this.subDeptCode = subDeptCode;
	}

	public String getIssueSlipNo() {
		return issueSlipNo;
	}

	public void setIssueSlipNo(String issueSlipNo) {
		this.issueSlipNo = issueSlipNo;
	}

	@Override
	public String toString() {
		return "IssueReport [issueId=" + issueId + ", issueNo=" + issueNo + ", itemCategory=" + itemCategory
				+ ", issueDate=" + issueDate + ", deleteStatus=" + deleteStatus + ", status=" + status + ", deptId="
				+ deptId + ", subDeptId=" + subDeptId + ", deptCode=" + deptCode + ", subDeptCode=" + subDeptCode
				+ ", issueSlipNo=" + issueSlipNo + ", issueReportDetailList=" + issueReportDetailList + "]";
	}

}
