package com.ats.tril.repository.billbook;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.tril.model.billbook.BillReceiptHeaderDisplay;

public interface BillReceiptHeaderDisplayRepo  extends JpaRepository<BillReceiptHeaderDisplay, Integer>{
	
	
	@Query(value = "SELECT\r\n" + 
			"    h.br_id,\r\n" + 
			"    DATE_FORMAT(h.br_date, '%d-%m-%Y') AS br_date,\r\n" + 
			"    h.br_date_time,\r\n" + 
			"    h.vendor_id,\r\n" + 
			"    v.vendor_name,\r\n" + 
			"    h.br_no,\r\n" + 
			"    h.amt,\r\n" + 
			"    h.pay_mode,\r\n" + 
			"    h.pay_desc,\r\n" + 
			"    h.remark,\r\n" + 
			"    h.user_id,\r\n" + 
			"    h.del_status,\r\n" + 
			"    h.ex_int1,\r\n" + 
			"    h.ex_int2,\r\n" + 
			"    h.ex_var1,\r\n" + 
			"    h.ex_var2,\r\n" + 
			"    h.ex_float1,\r\n" + 
			"    h.ex_float2,\r\n" + 
			"    u.usr_name as user_name\r\n" + 
			"FROM\r\n" + 
			"    t_bill_receipt_header h,\r\n" + 
			"    m_vendor v,\r\n" + 
			"    m_user u\r\n" + 
			"WHERE\r\n" + 
			"    h.del_status = 0 AND h.vendor_id = v.vendor_id AND v.deleted_in = 0 AND h.br_date BETWEEN :fromDate AND :toDate AND h.vendor_id IN(:vendId) AND h.user_id = u.usr_id", nativeQuery = true)
	List<BillReceiptHeaderDisplay> getSettledBillReceiptList(@Param("fromDate") String fromDate, @Param("toDate") String toDate ,@Param("vendId") List<String> vendId);

	
	@Query(value = "SELECT\r\n" + 
			"    h.br_id,\r\n" + 
			"    DATE_FORMAT(h.br_date, '%d-%m-%Y') AS br_date,\r\n" + 
			"    h.br_date_time,\r\n" + 
			"    h.vendor_id,\r\n" + 
			"    v.vendor_name,\r\n" + 
			"    h.br_no,\r\n" + 
			"    h.amt,\r\n" + 
			"    h.pay_mode,\r\n" + 
			"    h.pay_desc,\r\n" + 
			"    h.remark,\r\n" + 
			"    h.user_id,\r\n" + 
			"    h.del_status,\r\n" + 
			"    h.ex_int1,\r\n" + 
			"    h.ex_int2,\r\n" + 
			"    h.ex_var1,\r\n" + 
			"    h.ex_var2,\r\n" + 
			"    h.ex_float1,\r\n" + 
			"    h.ex_float2,\r\n" + 
			"    u.usr_name as user_name\r\n" + 
			"FROM\r\n" + 
			"    t_bill_receipt_header h,\r\n" + 
			"    m_vendor v,\r\n" + 
			"    m_user u\r\n" + 
			"WHERE\r\n" + 
			"    h.del_status = 0 AND h.vendor_id = v.vendor_id AND v.deleted_in = 0 AND h.br_id=:brId AND h.user_id = u.usr_id", nativeQuery = true)
	BillReceiptHeaderDisplay getSettledBillReceipt(@Param("brId") int brId);

}
