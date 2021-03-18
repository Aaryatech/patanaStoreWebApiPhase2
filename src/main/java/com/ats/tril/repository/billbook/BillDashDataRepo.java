package com.ats.tril.repository.billbook;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.tril.model.billbook.BillDashData;


public interface BillDashDataRepo extends JpaRepository<BillDashData, Integer>{
	
	@Query(value = " SELECT\r\n" + 
			"    bill_id,\r\n" + 
			"    vendor_id,\r\n" + 
			"    vendor_name,\r\n" + 
			"    bill_book_no,\r\n" + 
			"    bill_no,\r\n" + 
			"    bill_date,\r\n" + 
			"    total_value,\r\n" + 
			"    days,\r\n" + 
			"    expiry_date,\r\n" + 
			"    paid_amt\r\n" + 
			"FROM\r\n" + 
			"    (\r\n" + 
			"    SELECT\r\n" + 
			"        h.bill_id,\r\n" + 
			"        v.vendor_id,\r\n" + 
			"        v.vendor_name,\r\n" + 
			"        h.bill_no AS bill_book_no,\r\n" + 
			"        h.bill_remark AS bill_no,\r\n" + 
			"        DATE_FORMAT(h.bill_date, '%d-%m-%Y') bill_date,\r\n" + 
			"        h.total_value,\r\n" + 
			"        v.created_in AS days,\r\n" + 
			"        DATE_FORMAT(\r\n" + 
			"            DATE_ADD(\r\n" + 
			"                h.bill_date,\r\n" + 
			"                INTERVAL v.created_in DAY\r\n" + 
			"            ),\r\n" + 
			"            '%d-%m-%Y'\r\n" + 
			"        ) AS expiry_date,\r\n" + 
			"        (\r\n" + 
			"        SELECT\r\n" + 
			"            COALESCE(ROUND(SUM(bill_paid),\r\n" + 
			"            2),\r\n" + 
			"            0)\r\n" + 
			"        FROM\r\n" + 
			"            t_bill_receipt_detail\r\n" + 
			"        WHERE\r\n" + 
			"            del_status = 0 AND bill_id = h.bill_id\r\n" + 
			"    ) AS paid_amt,\r\n" + 
			"        \r\n" + 
			"        \r\n" + 
			"            DATE_ADD(\r\n" + 
			"                h.bill_date,\r\n" + 
			"                INTERVAL v.created_in DAY\r\n" + 
			"            )\r\n" + 
			"             AS temp_expiry_date\r\n" + 
			"FROM\r\n" + 
			"    t_bill_book_header h,\r\n" + 
			"    m_vendor v\r\n" + 
			"WHERE\r\n" + 
			"    h.vend_id = v.vendor_id AND h.del_status = 1 AND v.deleted_in = 0 AND h.bill_status != 2\r\n" + 
			"ORDER BY\r\n" + 
			"    expiry_date\r\n" + 
			"DESC\r\n" + 
			") t1 WHERE t1.temp_expiry_date <= DATE_ADD(CURDATE(), INTERVAL (SELECT value from t_setting WHERE setting_id=11) DAY)", nativeQuery = true)
	List<BillDashData> getPendingBillsForDashboard();
	
	
	
	@Query(value = "SELECT\r\n" + 
			"    h.bill_id,\r\n" + 
			"    v.vendor_id,\r\n" + 
			"    v.vendor_name,\r\n" + 
			"    h.bill_no AS bill_book_no,\r\n" + 
			"    h.bill_remark AS bill_no,\r\n" + 
			"    DATE_FORMAT(h.bill_date, '%d-%m-%Y') bill_date,\r\n" + 
			"    h.total_value,\r\n" + 
			"    v.created_in AS days,\r\n" + 
			"    DATE_FORMAT(\r\n" + 
			"        DATE_ADD(\r\n" + 
			"            h.bill_date,\r\n" + 
			"            INTERVAL v.created_in DAY\r\n" + 
			"        ),\r\n" + 
			"        '%d-%m-%Y'\r\n" + 
			"    ) AS expiry_date,\r\n" + 
			"    (\r\n" + 
			"    SELECT\r\n" + 
			"        COALESCE(ROUND(SUM(bill_paid),\r\n" + 
			"        2),\r\n" + 
			"        0)\r\n" + 
			"    FROM\r\n" + 
			"        t_bill_receipt_detail\r\n" + 
			"    WHERE\r\n" + 
			"        del_status = 0 AND bill_id = h.bill_id\r\n" + 
			") AS paid_amt\r\n" + 
			"FROM\r\n" + 
			"    t_bill_book_header h,\r\n" + 
			"    m_vendor v\r\n" + 
			"WHERE\r\n" + 
			"    h.vend_id = v.vendor_id AND h.del_status = 1 AND v.deleted_in = 0 AND h.vend_id =:vendId AND h.bill_status IN(0, 1)\r\n" + 
			"ORDER BY\r\n" + 
			"    expiry_date\r\n" + 
			"DESC\r\n" + 
			"    ", nativeQuery = true)
	List<BillDashData> getBillsByVendAndStatus(@Param("vendId") int vendId);
	

}
