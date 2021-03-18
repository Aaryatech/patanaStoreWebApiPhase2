package com.ats.tril.repository.billbook;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ats.tril.model.billbook.BillReceiptHeader;

public interface BillReceiptHeaderRepo extends JpaRepository<BillReceiptHeader, Integer>{

	@Query(value="select count(*) from t_bill_receipt_header where del_status=0 ",nativeQuery=true)
	int getBillReceiptListCount();
	
}
