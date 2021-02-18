package com.ats.tril.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MonthItemWiseMrnReportRepository extends JpaRepository<MonthItemWiseMrnReport, Integer>{

	
	@Query(value=("SELECT\r\n" + 
			"        @a\\:=@a+1 sr ,\r\n" + 
			"        month(:fromDate) as month_no,\r\n" + 
			"        monthname(:fromDate) as month_name,\r\n" + 
			"        m_item.item_id,\r\n" + 
			"        concat(m_item.item_code,\r\n" + 
			"        ' ',\r\n" + 
			"        m_item.item_desc) as item_code, \r\n" + 
			"        coalesce((Select\r\n" + 
			"            SUM(t_mrn_detail.approve_qty)                     \r\n" + 
			"        FROM\r\n" + 
			"            t_mrn_detail,\r\n" + 
			"            t_mrn_header,\r\n" + 
			"            po_header,\r\n" + 
			"            indent                     \r\n" + 
			"        where\r\n" + 
			"            t_mrn_header.mrn_date between :fromDate and :toDate                            \r\n" + 
			"            AND t_mrn_header.mrn_id=t_mrn_detail.mrn_id                             \r\n" + 
			"            AND m_item.item_id=t_mrn_detail.item_id                             \r\n" + 
			"            and t_mrn_header.del_status=1                             \r\n" + 
			"            and t_mrn_detail.del_status=1  \r\n" + 
			"            and t_mrn_detail.po_id=po_header.po_id \r\n" + 
			"            and po_header.ind_id=indent.ind_m_id \r\n" + 
			"            and indent.ind_isdev in (:isDev) AND t_mrn_detail.mrn_detail_status = 4),\r\n" + 
			"        0) AS approve_qty,\r\n" + 
			"        coalesce((Select\r\n" + 
			"            SUM(po_detail.item_rate*t_mrn_detail.approve_qty)                     \r\n" + 
			"        FROM\r\n" + 
			"            t_mrn_detail,\r\n" + 
			"            t_mrn_header,\r\n" + 
			"            po_detail,\r\n" + 
			"            po_header,\r\n" + 
			"            indent                      \r\n" + 
			"        where\r\n" + 
			"            t_mrn_header.mrn_date between :fromDate and :toDate                           \r\n" + 
			"            AND t_mrn_header.mrn_id=t_mrn_detail.mrn_id                             \r\n" + 
			"            AND m_item.item_id=t_mrn_detail.item_id                             \r\n" + 
			"            and t_mrn_header.del_status=1                             \r\n" + 
			"            and t_mrn_detail.del_status=1 \r\n" + 
			"            and po_detail.po_detail_id=t_mrn_detail.po_detail_id \r\n" + 
			"            and t_mrn_detail.po_id=po_header.po_id \r\n" + 
			"            and po_header.ind_id=indent.ind_m_id \r\n" + 
			"            and indent.ind_isdev in (:isDev) AND t_mrn_detail.mrn_detail_status = 4),\r\n" + 
			"        0) AS approved_qty_value,\r\n" + 
			"        coalesce((Select\r\n" + 
			"            SUM((po_detail.landing_cost/po_detail.item_qty)*t_mrn_detail.approve_qty)                     \r\n" + 
			"        FROM\r\n" + 
			"            t_mrn_detail,\r\n" + 
			"            t_mrn_header,\r\n" + 
			"            po_detail,\r\n" + 
			"            po_header,\r\n" + 
			"            indent  \r\n" + 
			"        where\r\n" + 
			"            t_mrn_header.mrn_date between :fromDate and :toDate                           \r\n" + 
			"            AND t_mrn_header.mrn_id=t_mrn_detail.mrn_id                             \r\n" + 
			"            AND m_item.item_id=t_mrn_detail.item_id                             \r\n" + 
			"            and t_mrn_header.del_status=1                             \r\n" + 
			"            and t_mrn_detail.del_status=1 \r\n" + 
			"            and po_detail.po_detail_id=t_mrn_detail.po_detail_id \r\n" + 
			"            and t_mrn_detail.po_id=po_header.po_id \r\n" + 
			"            and po_header.ind_id=indent.ind_m_id \r\n" + 
			"            and indent.ind_isdev in (:isDev) AND t_mrn_detail.mrn_detail_status = 4),\r\n" + 
			"        0) AS approved_landing_value\r\n" + 
			"    FROM\r\n" + 
			"        m_item,\r\n" + 
			"        (SELECT @a\\:=:index) AS a             \r\n" + 
			"    where\r\n" + 
			"        m_item.is_used=1 and m_item.cat_id=:catId"),nativeQuery=true)
	List<MonthItemWiseMrnReport> MrnMonthItemReport(@Param("fromDate")String fromDate,@Param("toDate") String lastDate,
			@Param("catId") int catId, @Param("isDev")List<Integer> isDev,@Param("index") int index);

	@Query(value=("SELECT\r\n" + 
			"        @a\\:=@a+1 sr ,\r\n" + 
			"        month(:fromDate) as month_no,\r\n" + 
			"        monthname(:fromDate) as month_name,\r\n" + 
			"        m_item.item_id,\r\n" + 
			"        concat(m_item.item_code,\r\n" + 
			"        ' ',\r\n" + 
			"        m_item.item_desc) as item_code, \r\n" + 
			"        coalesce((Select\r\n" + 
			"            SUM(t_mrn_detail.approve_qty)                     \r\n" + 
			"        FROM\r\n" + 
			"            t_mrn_detail,\r\n" + 
			"            t_mrn_header,\r\n" + 
			"            po_header,\r\n" + 
			"            indent                     \r\n" + 
			"        where\r\n" + 
			"            t_mrn_header.mrn_date between :fromDate and :toDate                            \r\n" + 
			"            AND t_mrn_header.mrn_id=t_mrn_detail.mrn_id                             \r\n" + 
			"            AND m_item.item_id=t_mrn_detail.item_id                             \r\n" + 
			"            and t_mrn_header.del_status=1                             \r\n" + 
			"            and t_mrn_detail.del_status=1\r\n" + 
			"            and t_mrn_header.mrn_type=:typeId\r\n" + 
			"            and t_mrn_detail.po_id=po_header.po_id \r\n" + 
			"            and po_header.ind_id=indent.ind_m_id \r\n" + 
			"            and indent.ind_isdev in (:isDev) AND t_mrn_detail.mrn_detail_status = 4),\r\n" + 
			"        0) AS approve_qty,\r\n" + 
			"        coalesce((Select\r\n" + 
			"            SUM(po_detail.item_rate*t_mrn_detail.approve_qty)                     \r\n" + 
			"        FROM\r\n" + 
			"            t_mrn_detail,\r\n" + 
			"            t_mrn_header,\r\n" + 
			"            po_detail,\r\n" + 
			"            po_header,\r\n" + 
			"            indent                      \r\n" + 
			"        where\r\n" + 
			"            t_mrn_header.mrn_date between :fromDate and :toDate                           \r\n" + 
			"            AND t_mrn_header.mrn_id=t_mrn_detail.mrn_id                             \r\n" + 
			"            AND m_item.item_id=t_mrn_detail.item_id                             \r\n" + 
			"            and t_mrn_header.del_status=1                             \r\n" + 
			"            and t_mrn_detail.del_status=1 \r\n" + 
			"            and t_mrn_header.mrn_type=:typeId\r\n" + 
			"            and po_detail.po_detail_id=t_mrn_detail.po_detail_id \r\n" + 
			"            and t_mrn_detail.po_id=po_header.po_id \r\n" + 
			"            and po_header.ind_id=indent.ind_m_id \r\n" + 
			"            and indent.ind_isdev in (:isDev) AND t_mrn_detail.mrn_detail_status = 4),\r\n" + 
			"        0) AS approved_qty_value,\r\n" + 
			"        coalesce((Select\r\n" + 
			"            SUM((po_detail.landing_cost/po_detail.item_qty)*t_mrn_detail.approve_qty)                     \r\n" + 
			"        FROM\r\n" + 
			"            t_mrn_detail,\r\n" + 
			"            t_mrn_header,\r\n" + 
			"            po_detail,\r\n" + 
			"            po_header,\r\n" + 
			"            indent  \r\n" + 
			"        where\r\n" + 
			"            t_mrn_header.mrn_date between :fromDate and :toDate                           \r\n" + 
			"            AND t_mrn_header.mrn_id=t_mrn_detail.mrn_id                             \r\n" + 
			"            AND m_item.item_id=t_mrn_detail.item_id                             \r\n" + 
			"            and t_mrn_header.del_status=1                             \r\n" + 
			"            and t_mrn_detail.del_status=1\r\n" + 
			"            and t_mrn_header.mrn_type=:typeId\r\n" + 
			"            and po_detail.po_detail_id=t_mrn_detail.po_detail_id \r\n" + 
			"            and t_mrn_detail.po_id=po_header.po_id \r\n" + 
			"            and po_header.ind_id=indent.ind_m_id \r\n" + 
			"            and indent.ind_isdev in (:isDev) AND t_mrn_detail.mrn_detail_status = 4),\r\n" + 
			"        0) AS approved_landing_value\r\n" + 
			"    FROM\r\n" + 
			"        m_item,\r\n" + 
			"        (SELECT @a\\:=:index) AS a             \r\n" + 
			"    where\r\n" + 
			"        m_item.is_used=1\r\n" + 
			"        and m_item.cat_id=:catId"),nativeQuery=true)
	List<MonthItemWiseMrnReport> MrnMonthItemReportWithTypeId(@Param("fromDate")String fromDate,@Param("toDate") String lastDate,
			@Param("catId")int catId,@Param("typeId") int typeId, @Param("isDev")List<Integer> isDev,@Param("index") int index);

	
	@Query(value=("SELECT\r\n" + 
			"        @a\\:=@a+1 sr ,\r\n" + 
			"        month(:fromDate) as month_no,\r\n" + 
			"        monthname(:fromDate) as month_name,\r\n" + 
			"        m_item.item_id,\r\n" + 
			"        concat(m_item.item_code,\r\n" + 
			"        ' ',\r\n" + 
			"        m_item.item_desc) as item_code, \r\n" + 
			"        coalesce((Select\r\n" + 
			"            SUM(t_mrn_detail.approve_qty)                     \r\n" + 
			"        FROM\r\n" + 
			"            t_mrn_detail,\r\n" + 
			"            t_mrn_header,\r\n" + 
			"            po_header,\r\n" + 
			"            indent                     \r\n" + 
			"        where\r\n" + 
			"            t_mrn_header.mrn_date between :fromDate and :toDate                            \r\n" + 
			"            AND t_mrn_header.mrn_id=t_mrn_detail.mrn_id                             \r\n" + 
			"            AND m_item.item_id=t_mrn_detail.item_id                             \r\n" + 
			"            and t_mrn_header.del_status=1                             \r\n" + 
			"            and t_mrn_detail.del_status=1\r\n" + 
			"            and t_mrn_header.mrn_type=:typeId\r\n" + 
			"            and t_mrn_detail.po_id=po_header.po_id \r\n" + 
			"            and po_header.ind_id=indent.ind_m_id \r\n" + 
			"            and indent.ind_isdev in (:isDev) and indent.dept_id=:deptId AND t_mrn_detail.mrn_detail_status = 4),\r\n" + 
			"        0) AS approve_qty,\r\n" + 
			"        coalesce((Select\r\n" + 
			"            SUM(po_detail.item_rate*t_mrn_detail.approve_qty)                     \r\n" + 
			"        FROM\r\n" + 
			"            t_mrn_detail,\r\n" + 
			"            t_mrn_header,\r\n" + 
			"            po_detail,\r\n" + 
			"            po_header,\r\n" + 
			"            indent                      \r\n" + 
			"        where\r\n" + 
			"            t_mrn_header.mrn_date between :fromDate and :toDate                           \r\n" + 
			"            AND t_mrn_header.mrn_id=t_mrn_detail.mrn_id                             \r\n" + 
			"            AND m_item.item_id=t_mrn_detail.item_id                             \r\n" + 
			"            and t_mrn_header.del_status=1                             \r\n" + 
			"            and t_mrn_detail.del_status=1 \r\n" + 
			"            and t_mrn_header.mrn_type=:typeId\r\n" + 
			"            and po_detail.po_detail_id=t_mrn_detail.po_detail_id \r\n" + 
			"            and t_mrn_detail.po_id=po_header.po_id \r\n" + 
			"            and po_header.ind_id=indent.ind_m_id \r\n" + 
			"            and indent.ind_isdev in (:isDev) and indent.dept_id=:deptId AND t_mrn_detail.mrn_detail_status = 4),\r\n" + 
			"        0) AS approved_qty_value,\r\n" + 
			"        coalesce((Select\r\n" + 
			"            SUM((po_detail.landing_cost/po_detail.item_qty)*t_mrn_detail.approve_qty)                     \r\n" + 
			"        FROM\r\n" + 
			"            t_mrn_detail,\r\n" + 
			"            t_mrn_header,\r\n" + 
			"            po_detail,\r\n" + 
			"            po_header,\r\n" + 
			"            indent  \r\n" + 
			"        where\r\n" + 
			"            t_mrn_header.mrn_date between :fromDate and :toDate                           \r\n" + 
			"            AND t_mrn_header.mrn_id=t_mrn_detail.mrn_id                             \r\n" + 
			"            AND m_item.item_id=t_mrn_detail.item_id                             \r\n" + 
			"            and t_mrn_header.del_status=1                             \r\n" + 
			"            and t_mrn_detail.del_status=1\r\n" + 
			"            and t_mrn_header.mrn_type=:typeId\r\n" + 
			"            and po_detail.po_detail_id=t_mrn_detail.po_detail_id \r\n" + 
			"            and t_mrn_detail.po_id=po_header.po_id \r\n" + 
			"            and po_header.ind_id=indent.ind_m_id \r\n" + 
			"            and indent.ind_isdev in (:isDev) and indent.dept_id=:deptId AND t_mrn_detail.mrn_detail_status = 4),\r\n" + 
			"        0) AS approved_landing_value\r\n" + 
			"    FROM\r\n" + 
			"        m_item,\r\n" + 
			"        (SELECT @a\\:=:index) AS a             \r\n" + 
			"    where\r\n" + 
			"        m_item.is_used=1\r\n" + 
			"        and m_item.cat_id=:catId"),nativeQuery=true)
	List<MonthItemWiseMrnReport> MrnMonthItemReportWithTypeIdAndDeptId(@Param("fromDate")String fromDate,@Param("toDate") String lastDate,
			@Param("catId")int catId,@Param("typeId")int typeId,@Param("isDev") List<Integer> isDev,@Param("deptId") int deptId,
			@Param("index")int index);

	@Query(value=("SELECT\r\n" + 
			"        @a\\:=@a+1 sr ,\r\n" + 
			"        month(:fromDate) as month_no,\r\n" + 
			"        monthname(:fromDate) as month_name,\r\n" + 
			"        m_item.item_id,\r\n" + 
			"        concat(m_item.item_code,\r\n" + 
			"        ' ',\r\n" + 
			"        m_item.item_desc) as item_code, \r\n" + 
			"        coalesce((Select\r\n" + 
			"            SUM(t_mrn_detail.approve_qty)                     \r\n" + 
			"        FROM\r\n" + 
			"            t_mrn_detail,\r\n" + 
			"            t_mrn_header,\r\n" + 
			"            po_header,\r\n" + 
			"            indent                     \r\n" + 
			"        where\r\n" + 
			"            t_mrn_header.mrn_date between :fromDate and :toDate                            \r\n" + 
			"            AND t_mrn_header.mrn_id=t_mrn_detail.mrn_id                             \r\n" + 
			"            AND m_item.item_id=t_mrn_detail.item_id                             \r\n" + 
			"            and t_mrn_header.del_status=1                             \r\n" + 
			"            and t_mrn_detail.del_status=1\r\n" + 
			"            and t_mrn_header.mrn_type=:typeId\r\n" + 
			"            and t_mrn_detail.po_id=po_header.po_id \r\n" + 
			"            and po_header.ind_id=indent.ind_m_id  AND t_mrn_detail.mrn_detail_status = 4\r\n" + 
			"            and indent.ind_isdev in (:isDev) and indent.dept_id=:deptId and indent.sub_dept_id=:subDeptId),\r\n" + 
			"        0) AS approve_qty,\r\n" + 
			"        coalesce((Select\r\n" + 
			"            SUM(po_detail.item_rate*t_mrn_detail.approve_qty)                     \r\n" + 
			"        FROM\r\n" + 
			"            t_mrn_detail,\r\n" + 
			"            t_mrn_header,\r\n" + 
			"            po_detail,\r\n" + 
			"            po_header,\r\n" + 
			"            indent                      \r\n" + 
			"        where\r\n" + 
			"            t_mrn_header.mrn_date between :fromDate and :toDate                           \r\n" + 
			"            AND t_mrn_header.mrn_id=t_mrn_detail.mrn_id                             \r\n" + 
			"            AND m_item.item_id=t_mrn_detail.item_id                             \r\n" + 
			"            and t_mrn_header.del_status=1                             \r\n" + 
			"            and t_mrn_detail.del_status=1 \r\n" + 
			"            and t_mrn_header.mrn_type=:typeId\r\n" + 
			"            and po_detail.po_detail_id=t_mrn_detail.po_detail_id \r\n" + 
			"            and t_mrn_detail.po_id=po_header.po_id \r\n" + 
			"            and po_header.ind_id=indent.ind_m_id  AND t_mrn_detail.mrn_detail_status = 4\r\n" + 
			"            and indent.ind_isdev in (:isDev) and indent.dept_id=:deptId and indent.sub_dept_id=:subDeptId),\r\n" + 
			"        0) AS approved_qty_value,\r\n" + 
			"        coalesce((Select\r\n" + 
			"            SUM((po_detail.landing_cost/po_detail.item_qty)*t_mrn_detail.approve_qty)                     \r\n" + 
			"        FROM\r\n" + 
			"            t_mrn_detail,\r\n" + 
			"            t_mrn_header,\r\n" + 
			"            po_detail,\r\n" + 
			"            po_header,\r\n" + 
			"            indent  \r\n" + 
			"        where\r\n" + 
			"            t_mrn_header.mrn_date between :fromDate and :toDate                           \r\n" + 
			"            AND t_mrn_header.mrn_id=t_mrn_detail.mrn_id                             \r\n" + 
			"            AND m_item.item_id=t_mrn_detail.item_id                             \r\n" + 
			"            and t_mrn_header.del_status=1                             \r\n" + 
			"            and t_mrn_detail.del_status=1\r\n" + 
			"            and t_mrn_header.mrn_type=:typeId\r\n" + 
			"            and po_detail.po_detail_id=t_mrn_detail.po_detail_id \r\n" + 
			"            and t_mrn_detail.po_id=po_header.po_id \r\n" + 
			"            and po_header.ind_id=indent.ind_m_id  AND t_mrn_detail.mrn_detail_status = 4\r\n" + 
			"            and indent.ind_isdev in (:isDev) and indent.dept_id=:deptId and indent.sub_dept_id=:subDeptId),\r\n" + 
			"        0) AS approved_landing_value\r\n" + 
			"    FROM\r\n" + 
			"        m_item,\r\n" + 
			"        (SELECT @a\\:=:index) AS a             \r\n" + 
			"    where\r\n" + 
			"        m_item.is_used=1\r\n" + 
			"        and m_item.cat_id=:catId"),nativeQuery=true)
	List<MonthItemWiseMrnReport> MrnMonthItemReportWithTypeIdAndDeptIdAndSubDeptId(@Param("fromDate")String fromDate,@Param("toDate") String lastDate,
			@Param("catId")int catId,@Param("typeId") int typeId,@Param("isDev") List<Integer> isDev,@Param("deptId") int deptId,
			@Param("subDeptId")int subDeptId,@Param("index") int index);

	@Query(value=("SELECT\r\n" + 
			"        @a\\:=@a+1 sr ,\r\n" + 
			"        month(:fromDate) as month_no,\r\n" + 
			"        monthname(:fromDate) as month_name,\r\n" + 
			"        m_item.item_id,\r\n" + 
			"        concat(m_item.item_code,\r\n" + 
			"        ' ',\r\n" + 
			"        m_item.item_desc) as item_code, \r\n" + 
			"        coalesce((Select\r\n" + 
			"            SUM(t_mrn_detail.approve_qty)                     \r\n" + 
			"        FROM\r\n" + 
			"            t_mrn_detail,\r\n" + 
			"            t_mrn_header,\r\n" + 
			"            po_header,\r\n" + 
			"            indent                     \r\n" + 
			"        where\r\n" + 
			"            t_mrn_header.mrn_date between :fromDate and :toDate                            \r\n" + 
			"            AND t_mrn_header.mrn_id=t_mrn_detail.mrn_id                             \r\n" + 
			"            AND m_item.item_id=t_mrn_detail.item_id                             \r\n" + 
			"            and t_mrn_header.del_status=1                             \r\n" + 
			"            and t_mrn_detail.del_status=1 \r\n" + 
			"            and t_mrn_detail.po_id=po_header.po_id \r\n" + 
			"            and po_header.ind_id=indent.ind_m_id AND t_mrn_detail.mrn_detail_status = 4\r\n" + 
			"            and indent.ind_isdev in (:isDev) and indent.dept_id=:deptId and indent.sub_dept_id=:subDeptId),\r\n" + 
			"        0) AS approve_qty,\r\n" + 
			"        coalesce((Select\r\n" + 
			"            SUM(po_detail.item_rate*t_mrn_detail.approve_qty)                     \r\n" + 
			"        FROM\r\n" + 
			"            t_mrn_detail,\r\n" + 
			"            t_mrn_header,\r\n" + 
			"            po_detail,\r\n" + 
			"            po_header,\r\n" + 
			"            indent                      \r\n" + 
			"        where\r\n" + 
			"            t_mrn_header.mrn_date between :fromDate and :toDate                           \r\n" + 
			"            AND t_mrn_header.mrn_id=t_mrn_detail.mrn_id                             \r\n" + 
			"            AND m_item.item_id=t_mrn_detail.item_id                             \r\n" + 
			"            and t_mrn_header.del_status=1                             \r\n" + 
			"            and t_mrn_detail.del_status=1 \r\n" + 
			"            and po_detail.po_detail_id=t_mrn_detail.po_detail_id \r\n" + 
			"            and t_mrn_detail.po_id=po_header.po_id \r\n" + 
			"            and po_header.ind_id=indent.ind_m_id AND t_mrn_detail.mrn_detail_status = 4\r\n" + 
			"            and indent.ind_isdev in (:isDev) and indent.dept_id=:deptId and indent.sub_dept_id=:subDeptId),\r\n" + 
			"        0) AS approved_qty_value,\r\n" + 
			"        coalesce((Select\r\n" + 
			"            SUM((po_detail.landing_cost/po_detail.item_qty)*t_mrn_detail.approve_qty)                     \r\n" + 
			"        FROM\r\n" + 
			"            t_mrn_detail,\r\n" + 
			"            t_mrn_header,\r\n" + 
			"            po_detail,\r\n" + 
			"            po_header,\r\n" + 
			"            indent  \r\n" + 
			"        where\r\n" + 
			"            t_mrn_header.mrn_date between :fromDate and :toDate                           \r\n" + 
			"            AND t_mrn_header.mrn_id=t_mrn_detail.mrn_id                             \r\n" + 
			"            AND m_item.item_id=t_mrn_detail.item_id                             \r\n" + 
			"            and t_mrn_header.del_status=1                             \r\n" + 
			"            and t_mrn_detail.del_status=1 \r\n" + 
			"            and po_detail.po_detail_id=t_mrn_detail.po_detail_id \r\n" + 
			"            and t_mrn_detail.po_id=po_header.po_id \r\n" + 
			"            and po_header.ind_id=indent.ind_m_id AND t_mrn_detail.mrn_detail_status = 4\r\n" + 
			"            and indent.ind_isdev in (:isDev) and indent.dept_id=:deptId and indent.sub_dept_id=:subDeptId),\r\n" + 
			"        0) AS approved_landing_value\r\n" + 
			"    FROM\r\n" + 
			"        m_item,\r\n" + 
			"        (SELECT @a\\:=:index) AS a             \r\n" + 
			"    where\r\n" + 
			"        m_item.is_used=1\r\n" + 
			"        and m_item.cat_id=:catId"),nativeQuery=true)
	List<MonthItemWiseMrnReport> MrnMonthItemReportWithDeptIdAndSubDeptId(@Param("fromDate")String fromDate,@Param("toDate") String lastDate,
			@Param("catId")int catId,@Param("isDev") List<Integer> isDev,@Param("deptId") int deptId,@Param("subDeptId") int subDeptId,
			@Param("index")int index);

	@Query(value=("SELECT\r\n" + 
			"        @a\\:=@a+1 sr ,\r\n" + 
			"        month(:fromDate) as month_no,\r\n" + 
			"        monthname(:fromDate) as month_name,\r\n" + 
			"        m_item.item_id,\r\n" + 
			"        concat(m_item.item_code,\r\n" + 
			"        ' ',\r\n" + 
			"        m_item.item_desc) as item_code, \r\n" + 
			"        coalesce((Select\r\n" + 
			"            SUM(t_mrn_detail.approve_qty)                     \r\n" + 
			"        FROM\r\n" + 
			"            t_mrn_detail,\r\n" + 
			"            t_mrn_header,\r\n" + 
			"            po_header,\r\n" + 
			"            indent                     \r\n" + 
			"        where\r\n" + 
			"            t_mrn_header.mrn_date between :fromDate and :toDate                            \r\n" + 
			"            AND t_mrn_header.mrn_id=t_mrn_detail.mrn_id                             \r\n" + 
			"            AND m_item.item_id=t_mrn_detail.item_id                             \r\n" + 
			"            and t_mrn_header.del_status=1                             \r\n" + 
			"            and t_mrn_detail.del_status=1 \r\n" + 
			"            and t_mrn_detail.po_id=po_header.po_id \r\n" + 
			"            and po_header.ind_id=indent.ind_m_id \r\n" + 
			"            and indent.ind_isdev in (:isDev) and indent.dept_id=:deptId AND t_mrn_detail.mrn_detail_status = 4),\r\n" + 
			"        0) AS approve_qty,\r\n" + 
			"        coalesce((Select\r\n" + 
			"            SUM(po_detail.item_rate*t_mrn_detail.approve_qty)                     \r\n" + 
			"        FROM\r\n" + 
			"            t_mrn_detail,\r\n" + 
			"            t_mrn_header,\r\n" + 
			"            po_detail,\r\n" + 
			"            po_header,\r\n" + 
			"            indent                      \r\n" + 
			"        where\r\n" + 
			"            t_mrn_header.mrn_date between :fromDate and :toDate                           \r\n" + 
			"            AND t_mrn_header.mrn_id=t_mrn_detail.mrn_id                             \r\n" + 
			"            AND m_item.item_id=t_mrn_detail.item_id                             \r\n" + 
			"            and t_mrn_header.del_status=1                             \r\n" + 
			"            and t_mrn_detail.del_status=1 \r\n" + 
			"            and po_detail.po_detail_id=t_mrn_detail.po_detail_id \r\n" + 
			"            and t_mrn_detail.po_id=po_header.po_id \r\n" + 
			"            and po_header.ind_id=indent.ind_m_id \r\n" + 
			"            and indent.ind_isdev in (:isDev) and indent.dept_id=:deptId AND t_mrn_detail.mrn_detail_status = 4),\r\n" + 
			"        0) AS approved_qty_value,\r\n" + 
			"        coalesce((Select\r\n" + 
			"            SUM((po_detail.landing_cost/po_detail.item_qty)*t_mrn_detail.approve_qty)                     \r\n" + 
			"        FROM\r\n" + 
			"            t_mrn_detail,\r\n" + 
			"            t_mrn_header,\r\n" + 
			"            po_detail,\r\n" + 
			"            po_header,\r\n" + 
			"            indent  \r\n" + 
			"        where\r\n" + 
			"            t_mrn_header.mrn_date between :fromDate and :toDate                           \r\n" + 
			"            AND t_mrn_header.mrn_id=t_mrn_detail.mrn_id                             \r\n" + 
			"            AND m_item.item_id=t_mrn_detail.item_id                             \r\n" + 
			"            and t_mrn_header.del_status=1                             \r\n" + 
			"            and t_mrn_detail.del_status=1 \r\n" + 
			"            and po_detail.po_detail_id=t_mrn_detail.po_detail_id \r\n" + 
			"            and t_mrn_detail.po_id=po_header.po_id \r\n" + 
			"            and po_header.ind_id=indent.ind_m_id \r\n" + 
			"            and indent.ind_isdev in (:isDev) and indent.dept_id=:deptId AND t_mrn_detail.mrn_detail_status = 4),\r\n" + 
			"        0) AS approved_landing_value\r\n" + 
			"    FROM\r\n" + 
			"        m_item,\r\n" + 
			"        (SELECT @a\\:=:index) AS a             \r\n" + 
			"    where\r\n" + 
			"        m_item.is_used=1\r\n" + 
			"        and m_item.cat_id=:catId"),nativeQuery=true)
	List<MonthItemWiseMrnReport> MrnMonthItemReportWithDeptId(@Param("fromDate")String fromDate,@Param("toDate") String lastDate,
			@Param("catId")int catId,@Param("isDev") List<Integer> isDev,@Param("deptId") int deptId,@Param("index") int index);

}
