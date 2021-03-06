package com.ats.tril.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class GetDamage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "damage_id")
	private int damageId;
	
	@Column(name = "date")
	private Date date;

	@Column(name = "item_id")
	private int itemId;

	@Column(name = "qty")
	private float qty;

	@Column(name = "value")
	private float value;
	
	@Column(name = "reason")
	private String reason;
	
	@Column(name = "del_status")
	private int delStatus;
	
	@Column(name = "item_code")
	private String itemCode;
	
	@Column(name = "item_desc")
	private String itemDesc;
	
	@Column(name = "damage_no")
	private String damageNo;
	
	@Column(name = "mrn_detail_id")
	private int mrnDetailId;
	
	@Column(name = "mrn_id")
	private int mrnId;
	
	@Column(name = "extra1")
	private int extra1;
	
	@Column(name = "extra2")
	private int extra2;
	
	@Column(name = "float1")
	private float float1;
	
	@Column(name = "float2")
	private float float2;
	
	@Column(name = "var1")
	private String var1;
	
	@Column(name = "var2")
	private String var2;
	
	@Column(name = "vendor_code")
	private String vendorCode;
	
	@Column(name = "vendor_name")
	private String vendorName;

	public int getDamageId() {
		return damageId;
	}

	public void setDamageId(int damageId) {
		this.damageId = damageId;
	}
	
	@JsonFormat(locale = "hi", timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public float getQty() {
		return qty;
	}

	public void setQty(float qty) {
		this.qty = qty;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public String getDamageNo() {
		return damageNo;
	}

	public void setDamageNo(String damageNo) {
		this.damageNo = damageNo;
	}

	public int getMrnDetailId() {
		return mrnDetailId;
	}

	public void setMrnDetailId(int mrnDetailId) {
		this.mrnDetailId = mrnDetailId;
	}

	public int getMrnId() {
		return mrnId;
	}

	public void setMrnId(int mrnId) {
		this.mrnId = mrnId;
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

	public float getFloat1() {
		return float1;
	}

	public void setFloat1(float float1) {
		this.float1 = float1;
	}

	public float getFloat2() {
		return float2;
	}

	public void setFloat2(float float2) {
		this.float2 = float2;
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

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	@Override
	public String toString() {
		return "GetDamage [damageId=" + damageId + ", date=" + date + ", itemId=" + itemId + ", qty=" + qty + ", value="
				+ value + ", reason=" + reason + ", delStatus=" + delStatus + ", itemCode=" + itemCode + ", itemDesc="
				+ itemDesc + ", damageNo=" + damageNo + ", mrnDetailId=" + mrnDetailId + ", mrnId=" + mrnId
				+ ", extra1=" + extra1 + ", extra2=" + extra2 + ", float1=" + float1 + ", float2=" + float2 + ", var1="
				+ var1 + ", var2=" + var2 + ", vendorCode=" + vendorCode + ", vendorName=" + vendorName + "]";
	}
	
	

}
