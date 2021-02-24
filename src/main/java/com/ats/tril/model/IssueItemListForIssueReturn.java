package com.ats.tril.model;
 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id; 

@Entity
public class IssueItemListForIssueReturn {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "issue_detail_id")
	private int issueDetailId;

	@Column(name = "issue_id")
	private int issueId;

	@Column(name = "item_id")
	private int itemId;

	@Column(name = "item_issue_qty")
	private float itemIssueQty;
	
	@Column(name = "item_request_qty")
	private float itemRequestQty;
	
	@Column(name = "status")
	private int status; 

	@Column(name = "del_status")
	private int delStatus;

	@Column(name = "batch_no")
	private String batchNo;
	
	@Column(name = "mrn_detail_id")
	private int mrnDetailId;
	
	@Column(name = "item_desc")
	private String itemDesc;
	
	@Column(name = "item_code")
	private String itemCode;
	
	@Column(name = "item_uom")
	private String itemUom;
	
	@Column(name = "item_uom2")
	private int itemUom2;
	
	@Column(name = "uom2")
	private int uom2;
	
	@Column(name = "uom_ratio2")
	private float uomRatio2;
	
	@Column(name = "uom")
	private String uom;
	
	@Column(name = "issue_no")
	private String issueNo;

	public int getIssueDetailId() {
		return issueDetailId;
	}

	public void setIssueDetailId(int issueDetailId) {
		this.issueDetailId = issueDetailId;
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

	public float getItemIssueQty() {
		return itemIssueQty;
	}

	public void setItemIssueQty(float itemIssueQty) {
		this.itemIssueQty = itemIssueQty;
	}

	public float getItemRequestQty() {
		return itemRequestQty;
	}

	public void setItemRequestQty(float itemRequestQty) {
		this.itemRequestQty = itemRequestQty;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public int getMrnDetailId() {
		return mrnDetailId;
	}

	public void setMrnDetailId(int mrnDetailId) {
		this.mrnDetailId = mrnDetailId;
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

	public String getItemUom() {
		return itemUom;
	}

	public void setItemUom(String itemUom) {
		this.itemUom = itemUom;
	}

	public int getItemUom2() {
		return itemUom2;
	}

	public void setItemUom2(int itemUom2) {
		this.itemUom2 = itemUom2;
	}

	public int getUom2() {
		return uom2;
	}

	public void setUom2(int uom2) {
		this.uom2 = uom2;
	}

	public float getUomRatio2() {
		return uomRatio2;
	}

	public void setUomRatio2(float uomRatio2) {
		this.uomRatio2 = uomRatio2;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getIssueNo() {
		return issueNo;
	}

	public void setIssueNo(String issueNo) {
		this.issueNo = issueNo;
	}

	@Override
	public String toString() {
		return "IssueItemListForIssueReturn [issueDetailId=" + issueDetailId + ", issueId=" + issueId + ", itemId="
				+ itemId + ", itemIssueQty=" + itemIssueQty + ", itemRequestQty=" + itemRequestQty + ", status="
				+ status + ", delStatus=" + delStatus + ", batchNo=" + batchNo + ", mrnDetailId=" + mrnDetailId
				+ ", itemDesc=" + itemDesc + ", itemCode=" + itemCode + ", itemUom=" + itemUom + ", itemUom2="
				+ itemUom2 + ", uom2=" + uom2 + ", uomRatio2=" + uomRatio2 + ", uom=" + uom + ", issueNo=" + issueNo
				+ "]";
	}
	
	
	
}
