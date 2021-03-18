package com.ats.tril.repository.billbook;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.tril.model.billbook.BillSettledList;


public interface BillSettledListRepo extends JpaRepository<BillSettledList, Integer>{

	@Query(value = "SELECT\r\n" + 
			"    h.br_id,\r\n" + 
			"    h.br_no,\r\n" + 
			"    DATE_FORMAT(h.br_date, '%d-%m-%Y') AS br_date,\r\n" + 
			"    DATE_FORMAT(h.br_date_time, '%d-%m-%Y %h:%i %p') as br_date_time,\r\n" + 
			"    h.amt,\r\n" + 
			"    h.pay_mode,\r\n" + 
			"    h.pay_desc,\r\n" + 
			"    h.remark,\r\n" + 
			"    h.user_id,\r\n" + 
			"    u.usr_name AS user_name,\r\n" + 
			"    d.bill_total,\r\n" + 
			"    d.bill_pending,\r\n" + 
			"    d.bill_paid\r\n" + 
			"FROM\r\n" + 
			"    t_bill_receipt_header h,\r\n" + 
			"    t_bill_receipt_detail d,\r\n" + 
			"    m_user u\r\n" + 
			"WHERE\r\n" + 
			"    h.br_id = d.br_id AND h.user_id = u.usr_id AND h.del_status = 0 AND d.del_status = 0 AND d.bill_id =:billId", nativeQuery = true)
	List<BillSettledList> getBillSettledListById(@Param("billId") int billId);

	
}
