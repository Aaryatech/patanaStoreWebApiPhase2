package com.ats.tril.repository.report;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ats.tril.model.report.IssueReportDetail;

public interface IssueReportDetailRepository extends JpaRepository<IssueReportDetail, Integer> {

	/*
	 * @Query(value =
	 * "SELECT d.*,i.item_code,i.item_desc ,i.item_uom,dp.dept_desc,sd.sub_dept_desc,a.acc_head_desc FROM item_issue_detail d ,"
	 * +
	 * "m_item i ,m_dept dp,m_sub_dept sd,m_account_head a WHERE  d.issue_id IN(:issueIdList) AND i.item_id=d.item_id AND "
	 * +
	 * "dp.dept_id=d.dept_id AND sd.sub_dept_id=d.sub_dept_id AND a.acc_head_id=d. acc_head  and d.del_status=1"
	 * , nativeQuery = true)
	 */
	
	
	@Query(value = "SELECT\r\n"
			+ "        d.*,\r\n"
			+ "        i.item_code,\r\n"
			+ "        i.item_desc ,\r\n"
			+ "        u.uom as item_uom,\r\n"
			+ "        dp.dept_desc,\r\n"
			+ "        sd.sub_dept_desc,\r\n"
			+ "        a.acc_head_desc \r\n"
			+ "    FROM\r\n"
			+ "        item_issue_detail d ,\r\n"
			+ "        m_item i ,\r\n"
			+ "        m_dept dp,\r\n"
			+ "        m_sub_dept sd,\r\n"
			+ "        m_account_head a,\r\n"
			+ "        m_uom u\r\n"
			+ "    WHERE\r\n"
			+ "        d.issue_id IN(:issueIdList) \r\n"
			+ "        AND i.item_id=d.item_id \r\n"
			+ "        AND dp.dept_id=d.dept_id \r\n"
			+ "        AND sd.sub_dept_id=d.sub_dept_id \r\n"
			+ "        AND a.acc_head_id=d. acc_head  \r\n"
			+ "        and d.del_status=1\r\n"
			+ "        and i.uom2=u.uom_id", nativeQuery = true)
	List<IssueReportDetail> getIssueDetailReportList(@Param("issueIdList") List<Integer> issueIdList);

}
