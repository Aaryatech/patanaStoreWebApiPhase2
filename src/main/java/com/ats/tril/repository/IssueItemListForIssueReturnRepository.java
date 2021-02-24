package com.ats.tril.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ats.tril.model.IssueItemListForIssueReturn;

public interface IssueItemListForIssueReturnRepository extends JpaRepository<IssueItemListForIssueReturn, Integer> {

	@Query(value="select i.issue_detail_id,i.issue_id,ih.issue_no,i.item_id,i.item_issue_qty,"
			+ "i.item_request_qty,i.status,i.del_status,i.batch_no,i.mrn_detail_id,im.item_desc,"
			+ "im.item_code,im.item_uom,im.item_uom2,im.uom2,im.uom_ratio2,u.uom from "
			+ "item_issue_detail i,item_issue_header ih, m_item im,m_uom u where ih.issue_id=i.issue_id "
			+ "and ih.issue_date=:date and i.status=2 and i.del_status=1 and ih.status=2 and "
			+ "ih.delete_status=1 and i.item_id in (:itemIds) and i.item_id=im.item_id and u.uom_id=im.uom2",nativeQuery=true) 
	 List<IssueItemListForIssueReturn> getIssueItemListForReturnProcess(String date, List<Integer> itemIds);

}
