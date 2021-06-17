package com.ats.tril.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.tril.model.TallySyncData;

public interface TallySyncDataRepo extends JpaRepository<TallySyncData, Integer> {

	@Query(value = " SELECT\r\n" + 
			"    t1.bill_detail_id,\r\n" + 
			"    t1.bill_id,\r\n" + 
			"    t1.vendor,\r\n" + 
			"    t1.vendor_credit_days,\r\n" + 
			"    t1.bill_book_no,\r\n" + 
			"    t1.date,\r\n" + 
			"    t1.bill_no,\r\n" + 
			"    t1.bill_date,\r\n" + 
			"    t1.item_desc AS product_name,\r\n" + 
			"    t1.item_uom AS uom,\r\n" + 
			"    t1.rate,\r\n" + 
			"    t1.qty,\r\n" + 
			"    t3.tot_taxable AS rate_total,\r\n" + 
			"    t3.cgst,\r\n" + 
			"    t3.sgst,\r\n" + 
			"    t3.igst,\r\n" + 
			"    t1.other_charges,\r\n" + 
			"    t1.disc_value,\r\n" + 
			"    ROUND(\r\n" + 
			"        (\r\n" + 
			"            t3.tot_taxable + t3.cgst + t3.sgst + t3.igst + t1.other_charges - t1.disc_value\r\n" + 
			"        ),\r\n" + 
			"        2\r\n" + 
			"    ) AS total_amount\r\n" + 
			"FROM\r\n" + 
			"    (\r\n" + 
			"    SELECT\r\n" + 
			"        d.bill_detail_id,\r\n" + 
			"        d.bill_id,\r\n" + 
			"        d.vend_id,\r\n" + 
			"        v.vendor_name AS vendor,\r\n" + 
			"        v.created_in AS vendor_credit_days,\r\n" + 
			"        h.bill_no AS bill_book_no,\r\n" + 
			"        DATE_FORMAT(h.bill_date, '%d-%m-%Y') AS DATE,\r\n" + 
			"        h.bill_remark as bill_no,\r\n" + 
			"        DATE_FORMAT(mh.bill_date, '%d-%m-%Y') AS bill_date,\r\n" + 
			"        d.item_id,\r\n" + 
			"        i.item_desc,\r\n" + 
			"        i.item_uom,\r\n" + 
			"        ROUND(COALESCE(d.item_rate, 0),\r\n" + 
			"        2) AS rate,\r\n" + 
			"        ROUND(COALESCE(d.mrn_qty, 0),\r\n" + 
			"        2) AS qty,\r\n" + 
			"        ROUND(COALESCE(d.basic_value, 0),\r\n" + 
			"        2) AS taxable_amt,\r\n" + 
			"        ROUND( COALESCE(h.other_charge_after, 0),\r\n" + 
			"            2\r\n" + 
			"        ) AS other_charges,\r\n" + 
			"        COALESCE(h.disc_value, 0) AS disc_value\r\n" + 
			"    FROM\r\n" + 
			"        t_bill_book_header h,\r\n" + 
			"        t_bill_book_detail d,\r\n" + 
			"        m_vendor v,\r\n" + 
			"        m_item i,\r\n" + 
			"        m_company c,\r\n" + 
			"        t_mrn_header mh\r\n" + 
			"    WHERE\r\n" + 
			"        h.bill_id = d.bill_id AND h.vend_id = v.vendor_id AND d.item_id = i.item_id AND  mh.mrn_id=h.mrn_id AND h.bill_date BETWEEN :fromDate AND :toDate AND c.com_id = 1\r\n" + 
			") t1\r\n" + 
			"LEFT JOIN(\r\n" + 
			"    SELECT\r\n" + 
			"        h.bill_id,\r\n" + 
			"        SUM(\r\n" + 
			"            ROUND(COALESCE(d.basic_value, 0),\r\n" + 
			"            2)\r\n" + 
			"        ) AS tot_taxable,\r\n" + 
			"        SUM(\r\n" + 
			"            ROUND(\r\n" + 
			"                (\r\n" + 
			"                    COALESCE(d.basic_value, 0) *(d.cgst / 100)\r\n" + 
			"                ),\r\n" + 
			"                2\r\n" + 
			"            )\r\n" + 
			"        ) AS cgst,\r\n" + 
			"        SUM(\r\n" + 
			"            ROUND(\r\n" + 
			"                (\r\n" + 
			"                    COALESCE(d.basic_value, 0) *(d.sgst / 100)\r\n" + 
			"                ),\r\n" + 
			"                2\r\n" + 
			"            )\r\n" + 
			"        ) AS sgst,\r\n" + 
			"        SUM(\r\n" + 
			"            ROUND(\r\n" + 
			"                (\r\n" + 
			"                    COALESCE(d.basic_value, 0) *(d.igst / 100)\r\n" + 
			"                ),\r\n" + 
			"                2\r\n" + 
			"            )\r\n" + 
			"        ) AS igst\r\n" + 
			"    FROM\r\n" + 
			"        t_bill_book_header h,\r\n" + 
			"        t_bill_book_detail d,\r\n" + 
			"        m_vendor v,\r\n" + 
			"        m_company c\r\n" + 
			"    WHERE\r\n" + 
			"        h.bill_id = d.bill_id AND h.vend_id = v.vendor_id AND c.com_id = 1 AND h.bill_date BETWEEN :fromDate AND :toDate\r\n" + 
			"    GROUP BY\r\n" + 
			"        h.bill_id\r\n" + 
			") t3\r\n" + 
			"ON\r\n" + 
			"    t1.bill_id = t3.bill_id", nativeQuery = true)
	List<TallySyncData> getTallySyncData(@Param("fromDate") String fromDate, @Param("toDate") String toDate);
	
	
}
