package com.ats.tril.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ats.tril.model.GetIssueReturn;

public interface GetIssueReturnRepository extends JpaRepository<GetIssueReturn, Integer>{

	@Query(value=("select ir.*,i.item_desc,i.item_code,u.uom from t_issue_return ir,m_item i,m_uom u where i.item_id=ir.item_id and"
			+ " u.uom_id=ir.issue_uom and ir.return_date between :fromDate and :toDate "),nativeQuery=true)
	List<GetIssueReturn> getIssueReturnList(String fromDate, String toDate);

}
