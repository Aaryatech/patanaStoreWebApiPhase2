package com.ats.tril.repository.billbook;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.ats.tril.model.billbook.BillReceiptDetail;

public interface BillReceiptDetailRepo  extends JpaRepository<BillReceiptDetail, Integer>{

	
	@Query(value = " SELECT COALESCE(ROUND(SUM(bill_paid),2),0) FROM t_bill_receipt_detail WHERE del_status=0 AND bill_id=:billId", nativeQuery = true)
	float getBillTotalPaidAmount(@Param("billId") int billId);
	
	@Query(value = "SELECT\r\n" + 
			"    br_detail_id,\r\n" + 
			"    br_id,\r\n" + 
			"    bill_id,\r\n" + 
			"    bill_no,\r\n" + 
			"    DATE_FORMAT(bill_date,'%d-%m-%Y') as bill_date,\r\n" + 
			"    bill_total,\r\n" + 
			"    bill_pending,\r\n" + 
			"    bill_paid,\r\n" + 
			"    del_status,\r\n" + 
			"    ex_int1,\r\n" + 
			"    ex_int2,\r\n" + 
			"    DATE_FORMAT(DATE_ADD(bill_date, INTERVAL ex_int1 DAY),'%d-%m-%Y') as ex_var1,\r\n" + 
			"    ex_var2,\r\n" + 
			"    ex_float1,\r\n" + 
			"    ex_float2\r\n" + 
			"FROM\r\n" + 
			"    t_bill_receipt_detail\r\n" + 
			"WHERE\r\n" + 
			"    del_status = 0 AND br_id =:brId", nativeQuery = true)
	List<BillReceiptDetail> getBillReceiptDetailListByBrId(@Param("brId") int brId);

	
}
