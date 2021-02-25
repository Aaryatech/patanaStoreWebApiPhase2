package com.ats.tril.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class GetIssueReturn {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;

	private int issueId;

	private int itemId;

	private int issueUom;

	private float returnQty;
	
	private int extra1;

	private int extra2;

	private String var1;

	private String var2;

	private Date returnDate;
	
	private String itemDesc;
	private String itemCode;
	private String uom;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIssueId() {
		return issueId;
	}
	public void setIssueId(int issueId) {
		this.issueId = issueId;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getIssueUom() {
		return issueUom;
	}
	public void setIssueUom(int issueUom) {
		this.issueUom = issueUom;
	}
	public float getReturnQty() {
		return returnQty;
	}
	public void setReturnQty(float returnQty) {
		this.returnQty = returnQty;
	}
	public int getExtra1() {
		return extra1;
	}
	public void setExtra1(int extra1) {
		this.extra1 = extra1;
	}
	public int getExtra2() {
		return extra2;
	}
	public void setExtra2(int extra2) {
		this.extra2 = extra2;
	}
	public String getVar1() {
		return var1;
	}
	public void setVar1(String var1) {
		this.var1 = var1;
	}
	public String getVar2() {
		return var2;
	}
	public void setVar2(String var2) {
		this.var2 = var2;
	}
	
	@JsonFormat(locale = "hi",timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy")
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	@Override
	public String toString() {
		return "GetIssueReturn [id=" + id + ", issueId=" + issueId + ", itemId=" + itemId + ", issueUom=" + issueUom
				+ ", returnQty=" + returnQty + ", extra1=" + extra1 + ", extra2=" + extra2 + ", var1=" + var1
				+ ", var2=" + var2 + ", returnDate=" + returnDate + ", itemDesc=" + itemDesc + ", itemCode=" + itemCode
				+ ", uom=" + uom + "]";
	}
	
	

}
