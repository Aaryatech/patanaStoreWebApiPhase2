package com.ats.tril.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "m_payment_terms")
public class PaymentTerms {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "pymt_term_id")
	private int pymtTermId;

	private String pymtDesc;

	private int isUsed;

	private int createdIn;

	private int deletedIn;
	
	private int days;

	public int getPymtTermId() {
		return pymtTermId;
	}

	public void setPymtTermId(int pymtTermId) {
		this.pymtTermId = pymtTermId;
	}

	public String getPymtDesc() {
		return pymtDesc;
	}

	public void setPymtDesc(String pymtDesc) {
		this.pymtDesc = pymtDesc;
	}

	public int getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(int isUsed) {
		this.isUsed = isUsed;
	}

	public int getCreatedIn() {
		return createdIn;
	}

	public void setCreatedIn(int createdIn) {
		this.createdIn = createdIn;
	}

	public int getDeletedIn() {
		return deletedIn;
	}

	public void setDeletedIn(int deletedIn) {
		this.deletedIn = deletedIn;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	@Override
	public String toString() {
		return "PaymentTerms [pymtTermId=" + pymtTermId + ", pymtDesc=" + pymtDesc + ", isUsed=" + isUsed
				+ ", createdIn=" + createdIn + ", deletedIn=" + deletedIn + ", days=" + days + "]";
	}

}
