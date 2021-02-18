package com.ats.tril.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "enq_header")
public class EnquiryHeader {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "enq_id")
	private int enqId;

	@Column(name = "enq_date")
	private String enqDate;

	@Column(name = "vend_id")
	private int vendId;

	@Column(name = "enq_remark")
	private String enqRemark;

	@Column(name = "enq_status")
	private int enqStatus;
	
	@Column(name = "del_status")
	private int delStatus;
	
	@Column(name = "enq_no")
	private String enqNo;
	
	@Column(name = "ind_no")
	private String indNo;
	
	@Column(name = "ind_id")
	private int indId;
	
	@Transient
	List<EnquiryDetail> enquiryDetailList;

	public int getEnqId() {
		return enqId;
	}

	public void setEnqId(int enqId) {
		this.enqId = enqId;
	}

	public String getEnqDate() {
		return enqDate;
	}

	public void setEnqDate(String enqDate) {
		this.enqDate = enqDate;
	}

	public int getVendId() {
		return vendId;
	}

	public void setVendId(int vendId) {
		this.vendId = vendId;
	}

	public String getEnqRemark() {
		return enqRemark;
	}

	public void setEnqRemark(String enqRemark) {
		this.enqRemark = enqRemark;
	}

	public int getEnqStatus() {
		return enqStatus;
	}

	public void setEnqStatus(int enqStatus) {
		this.enqStatus = enqStatus;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public List<EnquiryDetail> getEnquiryDetailList() {
		return enquiryDetailList;
	}

	public void setEnquiryDetailList(List<EnquiryDetail> enquiryDetailList) {
		this.enquiryDetailList = enquiryDetailList;
	}

	public String getEnqNo() {
		return enqNo;
	}

	public void setEnqNo(String enqNo) {
		this.enqNo = enqNo;
	}

	public String getIndNo() {
		return indNo;
	}

	public void setIndNo(String indNo) {
		this.indNo = indNo;
	}

	public int getIndId() {
		return indId;
	}

	public void setIndId(int indId) {
		this.indId = indId;
	}

	@Override
	public String toString() {
		return "EnquiryHeader [enqId=" + enqId + ", enqDate=" + enqDate + ", vendId=" + vendId + ", enqRemark="
				+ enqRemark + ", enqStatus=" + enqStatus + ", delStatus=" + delStatus + ", enqNo=" + enqNo + ", indNo="
				+ indNo + ", indId=" + indId + ", enquiryDetailList=" + enquiryDetailList + "]";
	}
	
	
	
	

}
