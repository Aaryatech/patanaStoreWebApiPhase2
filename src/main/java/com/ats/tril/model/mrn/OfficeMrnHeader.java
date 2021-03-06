package com.ats.tril.model.mrn;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "t_office_mrn_header")
public class OfficeMrnHeader {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "mrn_id")
	private int mrnId;
	
	private String mrnNo;
	
	private Date mrnDate;
	
	private int mrnType;
	
	private String vendorId;
	
	private String gateEntryNo;
	private Date gateEntryDate;
	
	private String docNo;//chalan no
	private Date docDate;//chalan Date
	
	
	private String billNo;
	private Date billDate;

	private int userId;
	
	private String transport;
	
	private String lrNo;
	
	private Date lrDate;
	private String remark1;
	private String remark2;
	
	private int mrnStatus;
	
	private int delStatus;
	
	private int poStatus;
	private int exInt1;
	private int exInt2;
	private String exVar1;
	private String exVar2;
	
	
	@Transient
	List<OfficeMrnDetail> mrnDetailList;


	public int getMrnId() {
		return mrnId;
	}


	public String getMrnNo() {
		return mrnNo;
	}


	public Date getMrnDate() {
		return mrnDate;
	}


	public int getMrnType() {
		return mrnType;
	}


	public String getVendorId() {
		return vendorId;
	}


	public String getGateEntryNo() {
		return gateEntryNo;
	}


	public Date getGateEntryDate() {
		return gateEntryDate;
	}


	public String getDocNo() {
		return docNo;
	}


	public Date getDocDate() {
		return docDate;
	}


	public String getBillNo() {
		return billNo;
	}


	public Date getBillDate() {
		return billDate;
	}


	public int getUserId() {
		return userId;
	}


	public String getTransport() {
		return transport;
	}


	public String getLrNo() {
		return lrNo;
	}


	public Date getLrDate() {
		return lrDate;
	}


	public String getRemark1() {
		return remark1;
	}


	public String getRemark2() {
		return remark2;
	}


	public int getMrnStatus() {
		return mrnStatus;
	}


	public int getDelStatus() {
		return delStatus;
	}


	public List<OfficeMrnDetail> getMrnDetailList() {
		return mrnDetailList;
	}


	public void setMrnId(int mrnId) {
		this.mrnId = mrnId;
	}


	public void setMrnNo(String mrnNo) {
		this.mrnNo = mrnNo;
	}


	public void setMrnDate(Date mrnDate) {
		this.mrnDate = mrnDate;
	}


	public void setMrnType(int mrnType) {
		this.mrnType = mrnType;
	}


	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}


	public void setGateEntryNo(String gateEntryNo) {
		this.gateEntryNo = gateEntryNo;
	}


	public void setGateEntryDate(Date gateEntryDate) {
		this.gateEntryDate = gateEntryDate;
	}


	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}


	public void setDocDate(Date docDate) {
		this.docDate = docDate;
	}


	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}


	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public void setTransport(String transport) {
		this.transport = transport;
	}


	public void setLrNo(String lrNo) {
		this.lrNo = lrNo;
	}


	public void setLrDate(Date lrDate) {
		this.lrDate = lrDate;
	}


	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}


	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}


	public void setMrnStatus(int mrnStatus) {
		this.mrnStatus = mrnStatus;
	}


	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}


	public void setMrnDetailList(List<OfficeMrnDetail> mrnDetailList) {
		this.mrnDetailList = mrnDetailList;
	}


	public int getPoStatus() {
		return poStatus;
	}


	public void setPoStatus(int poStatus) {
		this.poStatus = poStatus;
	}


	public int getExInt1() {
		return exInt1;
	}


	public void setExInt1(int exInt1) {
		this.exInt1 = exInt1;
	}


	public int getExInt2() {
		return exInt2;
	}


	public void setExInt2(int exInt2) {
		this.exInt2 = exInt2;
	}


	public String getExVar1() {
		return exVar1;
	}


	public void setExVar1(String exVar1) {
		this.exVar1 = exVar1;
	}


	public String getExVar2() {
		return exVar2;
	}


	public void setExVar2(String exVar2) {
		this.exVar2 = exVar2;
	}


	@Override
	public String toString() {
		return "OfficeMrnHeader [mrnId=" + mrnId + ", mrnNo=" + mrnNo + ", mrnDate=" + mrnDate + ", mrnType=" + mrnType
				+ ", vendorId=" + vendorId + ", gateEntryNo=" + gateEntryNo + ", gateEntryDate=" + gateEntryDate
				+ ", docNo=" + docNo + ", docDate=" + docDate + ", billNo=" + billNo + ", billDate=" + billDate
				+ ", userId=" + userId + ", transport=" + transport + ", lrNo=" + lrNo + ", lrDate=" + lrDate
				+ ", remark1=" + remark1 + ", remark2=" + remark2 + ", mrnStatus=" + mrnStatus + ", delStatus="
				+ delStatus + ", poStatus=" + poStatus + ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exVar1="
				+ exVar1 + ", exVar2=" + exVar2 + ", mrnDetailList=" + mrnDetailList + "]";
	}


}
